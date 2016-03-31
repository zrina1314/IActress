package com.sf.iactress.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sf.iactress.R;
import com.sf.iactress.analysis.XiannvwVideoAnalysisUtil;
import com.sf.iactress.base.BaseFragment;
import com.sf.iactress.base.BaseRecyclerViewAdapter;
import com.sf.iactress.base.Constants;
import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.bean.VideoBean;
import com.sf.iactress.net.controller.ServiceGenerator;
import com.sf.iactress.net.helper.GetMXianNvWService;
import com.sf.iactress.net.helper.GetXianNvWService;
import com.sf.iactress.ui.activity.WebPlay2Activity;
import com.sf.iactress.ui.activity.WebPlayActivity;
import com.sf.iactress.ui.adapter.AlbumAdapter;
import com.sf.iactress.ui.adapter.VideoAdapter;
import com.sf.iactress.ui.widget.DividerItemDecoration;
import com.sf.iactress.ui.widget.dialog.WaitLoadPictureDialog;
import com.sf.iactress.ui.widget.dialog.WaitLoadVideoDialog;
import com.sf.iactress.utils.ToastUtil;
import com.sf.sf_utils.LogUtil;
import com.sf.widget.superrecyclerview.OnMoreListener;
import com.sf.widget.superrecyclerview.SuperRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 花心大萝卜 on 2016/3/28.
 * 用途：视屏列表Fragment
 * 描述：
 */
public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, BaseRecyclerViewAdapter.OnItemClickListener {
    private static final String TAG = AlbumListFragment.class.getSimpleName();
    private List<VideoBean> mAlbumList = new ArrayList<>();
    SuperRecyclerView mRvAlbum;
    private VideoAdapter mAlbumAdapter;
    private int page = 1;
    private static final int COLUM_COUNT = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        mRvAlbum = (SuperRecyclerView) view.findViewById(R.id.srv_album);
        mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(COLUM_COUNT, StaggeredGridLayoutManager.VERTICAL));
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAlbumAdapter = new VideoAdapter(mContext, getColumWidth());
        mAlbumAdapter.setData(mAlbumList);
        mAlbumAdapter.setItemClickListener(this);
        mRvAlbum.setAdapter(mAlbumAdapter);
        mRvAlbum.setRefreshListener(this);
        mRvAlbum.setOnMoreListener(this);
        mRvAlbum.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRvAlbum.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void process() {
        getAlbumListByPage(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getAlbumListByPage(page);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        getAlbumListByPage(page);
    }


    public int getColumWidth() {
        //获取每列宽度
        WindowManager wm = (WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int spanWidth = display.getWidth() / COLUM_COUNT;
        return spanWidth;
    }


    private void getAlbumListByPage(final int page) {
        GetXianNvWService userClient = ServiceGenerator.createXianNvWService2(GetXianNvWService.class);
        Call<String> call = userClient.getVideoList(page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (!TextUtils.isEmpty(result)) {
                    List<VideoBean> tempAlbumBeanList = XiannvwVideoAnalysisUtil.getInstance().getAnalysisVideo(result);
                    if (tempAlbumBeanList != null && tempAlbumBeanList.size() > 0) {
                        if (page == 1)
                            mAlbumList.clear();
                        mAlbumList.addAll(tempAlbumBeanList);
                        if (page == 1)
                            mAlbumAdapter.notifyDataSetChanged();
                        else
                            mAlbumAdapter.notifyItemRangeInserted(mAlbumList.size() - tempAlbumBeanList.size(), mAlbumList.size());
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.getLogger().e(TAG, "加载网页失败了，失败原因：");
                LogUtil.getLogger().e(TAG, t.getMessage());
                mAlbumAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        final VideoBean bean = mAlbumList.get(position);
        LogUtil.getLogger().d(TAG, "点击index=【" + position + "】，数据=【" + bean.toString() + "】");
//        WaitLoadVideoDialog dialog = new WaitLoadVideoDialog(mContext, bean.getName(), bean.getLink());
//        dialog.show();
        showProgress();
        GetMXianNvWService getXianNvWService = ServiceGenerator.createMXianNvWService2(GetMXianNvWService.class);
        String id = String.valueOf(bean.getId());
        Call<String> call = getXianNvWService.getVideoSrc("article", id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissProgress();
                String result = response.body();
                if (!TextUtils.isEmpty(result)) {
                    String tempHtmlStr = XiannvwVideoAnalysisUtil.getInstance().getAnalysisVideoSrc(result);
                    if (!TextUtils.isEmpty(tempHtmlStr))
                        gotoDetailPage(bean.getName(), tempHtmlStr);
                    else
                        ToastUtil.showToast(mContext, "加载失败啦，不好意思啦");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissProgress();
                ToastUtil.showToast(mContext, "加载失败啦，不好意思啦");
            }
        });


//        Intent intent = new Intent(mContext, WebPlayActivity.class);
//        intent.putExtra(Constants.Param.WEB_VIEW_TITLE, bean.getName());
//        intent.putExtra(Constants.Param.WEB_VIEW_URL, Constants.XIANNVW_URL + bean.getLink());
//        mContext.startActivity(intent);
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String title, String htmlStr) {
        Intent intent = new Intent(mContext, WebPlay2Activity.class);
        intent.putExtra(Constants.Param.WEB_VIEW_TITLE, title);
        intent.putExtra(Constants.Param.WEB_VIEW_URL, htmlStr);
        mContext.startActivity(intent);
    }
}

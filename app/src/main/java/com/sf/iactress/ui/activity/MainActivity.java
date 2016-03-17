package com.sf.iactress.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.sf.iactress.R;
import com.sf.iactress.analysis.KanmxAnalysisUtil;
import com.sf.iactress.base.BaseActivity;
import com.sf.iactress.base.Constans;
import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.net.controller.ServiceGenerator;
import com.sf.iactress.net.helper.GetKanmxService;
import com.sf.iactress.ui.adapter.AlbumAdapter;
import com.sf.iactress.ui.widget.DividerItemDecoration;
import com.sf.sf_utils.LogUtil;
import com.sf.widget.superrecyclerview.OnMoreListener;
import com.sf.widget.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 花心大萝卜 on 2016/3/1.
 * 用途：
 * 描述：
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<AlbumBean> mAlbumList;
    @Bind(R.id.srv_album)
    SuperRecyclerView mRvAlbum;
    private AlbumAdapter mAlbumAdapter;
    private int page = 1;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(Constans.COLUM_COUNT, StaggeredGridLayoutManager.VERTICAL));
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAlbumAdapter = new AlbumAdapter(mAlbumList, getColumWidth());
        mRvAlbum.setAdapter(mAlbumAdapter);
        mRvAlbum.setRefreshListener(this);
        mRvAlbum.setOnMoreListener(this);
        mRvAlbum.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRvAlbum.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initData() {
        if (mAlbumList == null)
            mAlbumList = new ArrayList<>();
        getAlbumListByPage(page);
    }

    @Override
    protected void initTitleBar() {
        setLeftInvisible();
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void process() {
        loadAlbumListData(1);
    }

    /**
     * 加载数据
     */
    private void loadAlbumListData(int page) {
    }

    @Override
    public void onRefresh() {
//        mAlbumPresenter.getUser();
        page = 1;
        getAlbumListByPage(page);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        page++;
        getAlbumListByPage(page);
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String name) {
        //startActivity(NetworkDetailActivity.from(NetworkActivity.this, name));
    }

    public int getColumWidth() {
        //获取每列宽度
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int spanWidth = display.getWidth() / Constans.COLUM_COUNT;
        return spanWidth;
    }


    private void getAlbumListByPage(final int page) {
        GetKanmxService userClient = ServiceGenerator.createService2(GetKanmxService.class);
        Call<String> call = page == 1 ? userClient.getAlbumHomePageList() : userClient.getAlbumList(page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (!TextUtils.isEmpty(result)) {
                    LogUtil.getLogger().i(TAG, "加载网页成功，网页内容：");
                    LogUtil.getLogger().i(TAG, result);
                    List<AlbumBean> tempAlbumBeanList = KanmxAnalysisUtil.getInstance().getAnalysisAlbum(result);
                    if (tempAlbumBeanList != null && tempAlbumBeanList.size() > 0) {
                        LogUtil.getLogger().i(TAG, "解析相册成功，当前第" + page + "页，共有" + tempAlbumBeanList.size() + "条数据");
                        if (page == 1)
                            mAlbumList.clear();
                        mAlbumList.addAll(tempAlbumBeanList);
                    } else {
                        LogUtil.getLogger().i(TAG, "解析相册失败了，当前第" + page + "页");
                    }
                } else {
                    LogUtil.getLogger().e(TAG, "加载网页成功了，但数据为空：");
                    LogUtil.getLogger().e(TAG, result);
                }

                mAlbumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.getLogger().e(TAG, "加载网页失败了，失败原因：");
                LogUtil.getLogger().e(TAG, t.getMessage());
                mAlbumAdapter.notifyDataSetChanged();
            }
        });
    }
}

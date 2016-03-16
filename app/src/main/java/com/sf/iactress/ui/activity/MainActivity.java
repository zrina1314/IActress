package com.sf.iactress.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.sf.iactress.R;
import com.sf.iactress.base.BaseActivity;
import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.presenter.AlbumPresenter;
import com.sf.iactress.ui.adapter.AlbumAdapter;
import com.sf.iactress.ui.view.AlbumView;
import com.sf.iactress.ui.widget.DividerItemDecoration;
import com.sf.iactress.ui.widget.SpacesItemDecoration;
import com.sf.widget.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 花心大萝卜 on 2016/3/1.
 * 用途：
 * 描述：
 */
public class MainActivity extends BaseActivity implements AlbumView, SwipeRefreshLayout.OnRefreshListener {

    private List<AlbumBean> mAlbumList;
    @Bind(R.id.srv_album)
    SuperRecyclerView mRvAlbum;
    private AlbumAdapter mAlbumAdapter;
    private AlbumPresenter mAlbumPresenter;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置layoutManager
        mRvAlbum.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter

        mAlbumPresenter = new AlbumPresenter(MainActivity.this, this);
        initData();
        mAlbumAdapter = new AlbumAdapter(mAlbumList);
//        NetworkWrapper.getAlbums(getIntent().getStringExtra("1"), mAlbumAdapter);
        mRvAlbum.setAdapter(mAlbumAdapter);
        //设置item之间的间隔
        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext));
        mRvAlbum.setRefreshListener(this);
        mRvAlbum.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        mRvAlbum.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initData() {
        if (mAlbumList == null)
            mAlbumList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            mAlbumList.add(new AlbumBean(i + 1, i + "", i + ""));
//        }
        mAlbumPresenter.getUser();
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
    public void updateView(List<AlbumBean> user) {
        mAlbumList.addAll(user);
        mAlbumAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onRefresh() {

    }


    // 点击的回调
    public interface UserClickCallback {
        void onItemClicked(String name);
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String name) {
        //startActivity(NetworkDetailActivity.from(NetworkActivity.this, name));
    }
}

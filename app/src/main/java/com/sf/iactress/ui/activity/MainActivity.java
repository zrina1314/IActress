package com.sf.iactress.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.sf.iactress.R;
import com.sf.iactress.base.BaseFragmentActivity;
import com.sf.iactress.ui.fragment.AlbumListFragment;
import com.sf.iactress.ui.fragment.VideoListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 花心大萝卜 on 2016/3/1.
 * 用途：
 * 描述：
 */
public class MainActivity extends BaseFragmentActivity implements CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.rbtn_picture)
    RadioButton mRbtnPicture;
    @Bind(R.id.rbtn_video)
    RadioButton mRbtnVideo;

    private AlbumListFragment mAlbumFragment;
    private VideoListFragment mVideoListFragment;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initTitleBar() {
        setLeftInvisible();
    }

    @Override
    protected void initViewListener() {
        mRbtnPicture.setOnCheckedChangeListener(this);
        mRbtnVideo.setOnCheckedChangeListener(this);
    }

    @Override
    protected void process() {
        showAlbumFragment();
    }

    private void showAlbumFragment() {
        if (mAlbumFragment == null)
            mAlbumFragment = new AlbumListFragment();
        setFragment(mAlbumFragment);
    }

    private void showVideoListFragment() {
        if (mVideoListFragment == null)
            mVideoListFragment = new VideoListFragment();
        setFragment(mVideoListFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_content, fragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rbtn_picture:
                    showAlbumFragment();
                    break;
                case R.id.rbtn_video:
                    showVideoListFragment();
                    break;
            }
        }
    }
}
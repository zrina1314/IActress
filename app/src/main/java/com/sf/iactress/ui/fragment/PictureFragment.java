package com.sf.iactress.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sf.iactress.R;
import com.sf.iactress.base.Constans;
import com.sf.iactress.utils.ImageOptionUtil;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：
 * 描述：
 */
@SuppressLint("ValidFragment")
public class PictureFragment extends Fragment {
    private View mRootView;
    private Context mContext;
    private PhotoView mPhotoView;
    private ProgressBar mProgressBar;
    private String mPictureUrl;

    public PictureFragment(String picture) {
        this.mPictureUrl = picture;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_picture, container, false);
        mContext = getActivity();
        initView();
        process();
        return mRootView;
    }

    protected void initView() {
        mPhotoView = (PhotoView) mRootView.findViewById(R.id.photo_view);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
    }

    protected void process() {
        String url = Constans.KANMX_URL + mPictureUrl;
        ImageLoader.getInstance().displayImage(url, mPhotoView, ImageOptionUtil.defaultOptions, null, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                mProgressBar.setMax(total);
                mProgressBar.setProgress(current);
                mProgressBar.setVisibility(current == total ? View.GONE : View.VISIBLE);
            }
        });
    }
}

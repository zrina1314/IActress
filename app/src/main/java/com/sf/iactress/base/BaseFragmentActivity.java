package com.sf.iactress.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.iactress.R;
import com.sf.iactress.ui.widget.dialog.ProgressDialog;

public abstract class BaseFragmentActivity extends FragmentActivity implements IProgress {

    private static final String TAG = BaseFragmentActivity.class.getSimpleName();
    public Context mContext;
    private Dialog mProgressDialog;
    private String mOldProgressMessage = "";
    private View mTitleView;
    protected ImageView mTitleLeftImg, mTitleRightImg, mTitleMiddleImg;
    protected TextView mTitleLeftLabel, mTitleRightLabel, mTitleLabel;
    protected View mLeftImgLayout, mLeftLabelLayout, mRightImgLayout, mRightLabelLayout;
    /**
     * 当前Activity是否活动
     */
    boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutID());
        mContext = this;
        initHeadTitle();
        initView();
        initViewListener();
        process();
    }

    private void initHeadTitle() {
        mTitleView = findViewById(R.id.head_title);
        if (mTitleView != null) {
            mTitleLeftImg = (ImageView) findViewById(R.id.mLeftImg);
            mTitleRightImg = (ImageView) findViewById(R.id.mRightImg);
            mTitleMiddleImg = (ImageView) findViewById(R.id.mMiddleImg);
            mTitleLeftLabel = (TextView) findViewById(R.id.mLeftLabel);
            mTitleLabel = (TextView) findViewById(R.id.mMiddleLabel);
            mTitleRightLabel = (TextView) findViewById(R.id.mRightLabel);

            mLeftImgLayout = findViewById(R.id.mLeftImgLayout);
            mLeftLabelLayout = findViewById(R.id.mLeftLabelLayout);
            mRightImgLayout = findViewById(R.id.mRightImgLayout);
            mRightLabelLayout = findViewById(R.id.mRightLabelLayout);

            setLeftIcon(R.drawable.ic_back, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            initTitleBar();
        }
    }

    /**
     * 设置左边label
     */
    public void setLeftLabel(int resource_id, View.OnClickListener onClickListener) {
        if (mLeftLabelLayout != null) {
            if (resource_id == 0) {
                mLeftLabelLayout.setVisibility(View.GONE);
                mLeftImgLayout.setVisibility(View.GONE);
                return;
            }
            mLeftLabelLayout.setVisibility(View.VISIBLE);
            mLeftImgLayout.setVisibility(View.GONE);
            mTitleLeftLabel.setText(resource_id);
            mLeftLabelLayout.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置左边菜单按钮的图标
     */
    public void setLeftIcon(int resource_id, View.OnClickListener onClickListener) {
        if (mLeftImgLayout != null) {
            mLeftImgLayout.setVisibility(View.VISIBLE);
            mLeftLabelLayout.setVisibility(View.GONE);
            mTitleLeftImg.setImageResource(resource_id);
            mLeftImgLayout.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置左边label
     */
    public void setRightLabel(int resource_id, View.OnClickListener onClickListener) {
        if (mRightLabelLayout != null) {
            mRightLabelLayout.setVisibility(View.VISIBLE);
            mTitleRightLabel.setText(resource_id);
            mRightLabelLayout.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置右边菜单按钮的图标
     */
    public void setRightIcon(int resource_id, View.OnClickListener onClickListener) {
        if (mRightImgLayout != null) {
            mRightImgLayout.setVisibility(View.VISIBLE);
            mTitleRightImg.setImageResource(resource_id);
            mRightImgLayout.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置标题title
     */
    public void setTitle(int resource_id) {
        if (mTitleLabel != null) {
            mTitleLabel.setVisibility(View.VISIBLE);
            mTitleLabel.setText(resource_id);
        }
    }

    /**
     * 隐藏左边按钮
     */
    public void setLeftInvisible() {
        if (mLeftImgLayout != null)
            mLeftImgLayout.setVisibility(View.GONE);
        if (mLeftLabelLayout != null)
            mLeftLabelLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    /**
     * 生成主文件布局ID
     */
    protected abstract int initLayoutID();

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {

    }

    /**
     * 初始化页面控件。
     */
    protected abstract void initView();

    /**
     * 页面控件点击事件处理
     */
    protected abstract void initViewListener();

    /**
     * 逻辑处理
     */
    protected abstract void process();


    @Override
    public void showProgress() {
        if (mProgressDialog == null || !(mProgressDialog instanceof ProgressDialog)) {
            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setCancelable(isCancelable);
//            mOldProgressMessage = tips;
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();

//        showProgress(R.string.server_loading);
    }

    @Override
    public void dismissProgress() {
        if (isFinishing()) {
            mProgressDialog = null;
            return;
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

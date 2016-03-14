package com.sf.iactress.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sf.iactress.R;

/**
 * Activity管理的base类
 */
public abstract class BaseActivity extends Activity {

    private static String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;
    protected View mTitleView;
    protected ImageView mTitleLeftImg, mTitleRightImg, mSearchImg;
    protected TextView mTitleLeftLabel, mTitleLabel;
    protected TextView mTitleRightLabel;
    protected View mLeftImgLayout, mLeftLabelLayout, mRightImgLayout, mRightLabelLayout, mRightSearchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = mContext.getClass().getSimpleName();
        setContentView(initLayoutID());
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
            mTitleLeftLabel = (TextView) findViewById(R.id.mLeftLabel);
            mTitleLabel = (TextView) findViewById(R.id.mMiddleLabel);
            mTitleRightLabel = (TextView) findViewById(R.id.mRightLabel);
            mSearchImg = (ImageView) findViewById(R.id.mRightSearch);

            mLeftImgLayout = findViewById(R.id.mLeftImgLayout);
            mLeftLabelLayout = findViewById(R.id.mLeftLabelLayout);
            mRightImgLayout = findViewById(R.id.mRightImgLayout);
            mRightLabelLayout = findViewById(R.id.mRightLabelLayout);
            mRightSearchLayout = findViewById(R.id.mRightSearchLayout);

//            setLeftIcon(R.drawable.ic_back, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });

            initTitleBar();
        }
    }

    public void setTitleVisible(boolean isShow) {
        if (isShow) {
            mTitleView.setVisibility(View.VISIBLE);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
    }

    public void setLeftInvisible() {
        if (mLeftImgLayout != null)
            mLeftImgLayout.setVisibility(View.GONE);
        if (mLeftLabelLayout != null)
            mLeftLabelLayout.setVisibility(View.GONE);
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

    public void setRightLabelEnable(boolean flag) {
        mRightLabelLayout.setEnabled(flag);
        mTitleRightLabel.setTextColor(!flag ? getResources().getColor(R.color.Color_G) : getResources().getColor(R.color.Color_D));
    }

    public void setRightLabelInvisible() {
        if (mRightLabelLayout != null) {
            mRightLabelLayout.setVisibility(View.INVISIBLE);
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
     * 设置右边搜索按钮
     */
    public void setRightSearchIcon(int resource_id, View.OnClickListener onClickListener) {
        if (mRightSearchLayout != null) {
            mRightSearchLayout.setVisibility(View.VISIBLE);
            mSearchImg.setImageResource(resource_id);
            mRightSearchLayout.setOnClickListener(onClickListener);
        }
    }

    public void setRightSearhInvisible() {
        if (mRightSearchLayout != null) {
            mRightSearchLayout.setVisibility(View.GONE);
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
     * 设置标题title
     */
    public void setTitle(String resource) {
        if (mTitleLabel != null) {
            mTitleLabel.setVisibility(View.VISIBLE);
            mTitleLabel.setText(resource);
        }
    }


    /**
     * 设置布局
     */
    protected abstract int initLayoutID();

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {

    }

    /**
     * 初始化布局组件
     */
    protected abstract void initView();

    /**
     * 初始化组件监听事件
     */
    protected abstract void initViewListener();

    /**
     * 业务逻辑处理
     */
    protected abstract void process();
}

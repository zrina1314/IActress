package com.sf.iactress.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sf.iactress.R;
import com.sf.iactress.ui.widget.dialog.effects.BaseEffects;
import com.sf.iactress.ui.widget.dialog.effects.Effectstype;
import com.sf.sf_utils.LogUtil;

public abstract class BaseDialog extends Dialog implements DialogInterface {
    private static final String TAG = BaseDialog.class.getSimpleName();
    protected Context mContext;
    private View mDialogView;
    private int mDuration = -1;
    private int type = 0;

    public BaseDialog(Context context) {
        this(context, R.style.dialog_untran);
    }

    private BaseDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidth();
    }

    protected void initWidth() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = mContext.getResources().getDimensionPixelSize(R.dimen.x540);
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    private void init(Context context) {
        mContext = context;
        mDialogView = View.inflate(context, initLayoutID(), null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mDialogView);

        initView(mDialogView);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mDialogView.setVisibility(View.VISIBLE);
                if (type == 0) {
                    type = Effectstype.SLIDE_TOP;
                }
                start(type);

            }
        });
        initDialog();
    }

    /**
     * dialog布局id
     */
    protected abstract int initLayoutID();

    protected abstract void initDialog();

    protected abstract void initView(View view);

    @Override
    public void onDetachedFromWindow() {
        if (isShowing()) {
            dismiss();
        }
    }


    private void start(int type) {
        BaseEffects animator = Effectstype.getAnimator(type);
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mDialogView);
    }

    protected BaseDialog setEffect(int type) {
        this.type = type;
        return this;
    }

    protected void setDuration(int duration) {
        this.mDuration = duration;
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            LogUtil.getLogger().e(TAG, "打开Dialog时出错");
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (isShowing()) {
                super.dismiss();
            }
        } catch (Exception e) {
            LogUtil.getLogger().e(TAG, "关闭Dialog时出错");
            e.printStackTrace();
        }
    }
}

/**
 * ****************************************************************************
 * 系统名称   ：速运通App
 * 客户           ： 速运通研发人员
 * 文件名       ： AbstractNetworkWrapper.java
 * (C) Copyright sf_Express Corporation 2014
 * All Rights Reserved.
 * *****************************************************************************
 * 注意： 本内容仅限于顺丰速运资讯科技本部IT产品中心内部使用，禁止转发
 * ****************************************************************************
 */

package com.sf.iactress.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sf.iactress.R;
import com.sf.iactress.ui.widget.dialog.effects.Effectstype;

/**
 * 进度条对话框
 * @author 431896
 * @date 2014-5-20 上午10:00:01
 * @see
 * @since v5.0
 */
public class ProgressDialog extends BaseDialog {
    private SimpleDraweeView mImgLoad;
    private TextView mTvProgress;

    public ProgressDialog(Context context) {
        super(context);
        setOnKeyListener(keylistener);
    }

    @Override
    protected int initLayoutID() {
        return R.layout.view_dialog_wait_load_picture;
    }

    @Override
    protected void initDialog() {
        this.setCanceledOnTouchOutside(false);
        this.setDuration(500);
        this.setEffect(Effectstype.SLIDE_BOTTOM);
    }

    @Override
    protected void initView(View view) {
        mImgLoad = (SimpleDraweeView) view.findViewById(R.id.mDialogIcon);
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
        if (mImgLoad != null) {
            DraweeController controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setImageRequest(ImageRequestBuilder.newBuilderWithResourceId(R.drawable.ic_load_picture).build()).build();
            mImgLoad.setController(controller);
        }
    }

    OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}

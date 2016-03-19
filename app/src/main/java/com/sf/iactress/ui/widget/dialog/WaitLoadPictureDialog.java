package com.sf.iactress.ui.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sf.iactress.R;
import com.sf.iactress.analysis.KanmxPictureAnalysis;
import com.sf.iactress.ui.activity.PictureActivity;
import com.sf.iactress.ui.widget.dialog.effects.Effectstype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：等待加载图片Dialog
 * 描述：
 */
public class WaitLoadPictureDialog extends BaseDialog implements KanmxPictureAnalysis.AnalysisListener {
    private SimpleDraweeView mImgLoad;
    private TextView mTvProgress;
    private String mFirstPageUrl;

    public WaitLoadPictureDialog(Context context, String firstPageUrl) {
        super(context);
        this.mFirstPageUrl = firstPageUrl;
        loadPictures(mFirstPageUrl);
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
        setOnKeyListener(keylistener);
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

    /**
     * 加载图片信息
     *
     * @param url
     */
    private void loadPictures(String url) {
        KanmxPictureAnalysis kanmxPictureAnalysis = new KanmxPictureAnalysis(url);
        kanmxPictureAnalysis.startCrawler();
        kanmxPictureAnalysis.setAnalysisListener(this);
    }

    @Override
    public void analysisComplete(List<String> pictureList) {
        dismiss();
        gotoDetailPage((ArrayList<String>) pictureList);
    }

    @Override
    public void analysisProgress(int PictureSun) {
        mTvProgress.setText(mContext.getResources().getString(R.string.wait_load_picture_progress, PictureSun + ""));
    }

    // 跳转到库详情页面
    private void gotoDetailPage(ArrayList<String> pictureList) {
        Intent intent = new Intent(mContext, PictureActivity.class);
        intent.putExtra("pictures", pictureList);
        mContext.startActivity(intent);
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

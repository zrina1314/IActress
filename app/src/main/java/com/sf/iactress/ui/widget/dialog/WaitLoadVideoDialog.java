package com.sf.iactress.ui.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.sf.iactress.R;
import com.sf.iactress.analysis.XiannvwVideoAnalysisUtil;
import com.sf.iactress.base.Constants;
import com.sf.iactress.net.controller.ServiceGenerator;
import com.sf.iactress.net.helper.GetXianNvWService;
import com.sf.iactress.ui.activity.WebPlayActivity;
import com.sf.iactress.ui.widget.dialog.effects.Effectstype;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：等待加载图片Dialog
 * 描述：
 */
public class WaitLoadVideoDialog extends BaseDialog {
    private static final String TAG = WaitLoadVideoDialog.class.getSimpleName();
    private SimpleDraweeView mImgLoad;
    private TextView mTvProgress;
    private String mFirstPageUrl;

    public WaitLoadVideoDialog(Context context, String title, String firstPageUrl) {
        super(context);
        this.mFirstPageUrl = firstPageUrl;
        loadPictures(title, mFirstPageUrl);
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

    /**
     * 加载图片信息
     * @param url
     */
    private void loadPictures(final String title, String url) {
        GetXianNvWService getXianNvWService = ServiceGenerator.createXianNvWService2(GetXianNvWService.class);
        Call<String> call = getXianNvWService.getVideoDetail(url);
        final String finalUrl = url;
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismiss();
                String result = response.body();
                if (!TextUtils.isEmpty(result)) {
                    String tempHtmlStr = XiannvwVideoAnalysisUtil.getInstance().getAnalysisVideoSrc(result);
                    if (!TextUtils.isEmpty(tempHtmlStr))
                        gotoDetailPage(title, Constants.XIANNVW_URL + tempHtmlStr);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismiss();
            }
        });
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String title, String htmlStr) {
        Intent intent = new Intent(mContext, WebPlayActivity.class);
        intent.putExtra(Constants.Param.WEB_VIEW_TITLE, title);
        intent.putExtra(Constants.Param.WEB_VIEW_URL, htmlStr);
        mContext.startActivity(intent);
    }

    OnKeyListener keylistener = new OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };
}

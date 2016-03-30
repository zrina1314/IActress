package com.sf.iactress.ui.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.sf.iactress.R;
import com.sf.iactress.base.BaseActivity;
import com.sf.iactress.base.Constants;

/**
 * Created by 花心大萝卜 on 2016/3/28.
 * 用途：
 * 描述：
 */
public class WebPlay2Activity extends BaseActivity {

    private FrameLayout videoview;//全屏时视频加载view
    private WebView videowebview;
    private View xCustomView;
    private ProgressBar mProgressbar;
    private xWebChromeClient xwebchromeclient;
    private String url = "http://look.appjx.cn/mobile_api.php?mod=news&id=12604";
    private WebChromeClient.CustomViewCallback xCustomViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutID() {
        return R.layout.activity_web2_view;
    }

    @Override
    protected void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
        initwidget();
    }

    @Override
    protected void initViewListener() {
    }

    @Override
    protected void process() {
        String tempUrl = getIntent().getStringExtra(Constants.Param.WEB_VIEW_URL);
        String tempTitle = getIntent().getStringExtra(Constants.Param.WEB_VIEW_TITLE);
        if (!TextUtils.isEmpty(tempUrl))
            url = tempUrl;
        videowebview.loadUrl(url);
        if (!TextUtils.isEmpty(tempTitle))
            setTitle(tempTitle);
    }


    private void initwidget() {
        //TODOAuto-generatedmethodstub
        videoview = (FrameLayout) findViewById(R.id.video_view);
        videowebview = (WebView) findViewById(R.id.video_webview);
        WebSettings ws = videowebview.getSettings();
        /**
         *setAllowFileAccess启用或禁止WebView访问文件数据setBlockNetworkImage是否显示网络图像
         *setBuiltInZoomControls设置是否支持缩放setCacheMode设置缓冲的模式
         *setDefaultFontSize设置默认的字体大小setDefaultTextEncodingName设置在解码时使用的默认编码
         *setFixedFontFamily设置固定使用的字体setJavaSciptEnabled设置是否支持Javascript
         *setLayoutAlgorithm设置布局方式setLightTouchEnabled设置用鼠标激活被选项
         *setSupportZoom设置是否支持变焦
         **/
        ws.setBuiltInZoomControls(false);//隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//排版适应屏幕
        ws.setUseWideViewPort(true);//可任意比例缩放
        ws.setLoadWithOverviewMode(true);//setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);//保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);//启用地理定位
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");//设置定位的数据库路径
        ws.setDomStorageEnabled(true);
        xwebchromeclient = new xWebChromeClient();
        videowebview.setWebChromeClient(xwebchromeclient);
        videowebview.setWebViewClient(new xWebViewClientent());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            } else {
                videowebview.loadUrl("about:blank");
//  mTestWebView.loadData("","text/html;charset=UTF-8",null);
                WebPlay2Activity.this.finish();
                Log.i("testwebview", "===>>>2");
            }
        }
        return true;
    }

    /**
     * 判断是否是全屏
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }

    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     * @author
     */
    public class xWebChromeClient extends WebChromeClient

    {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
//播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            videowebview.setVisibility(View.GONE);
            //如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
//视频播放退出全屏会被调用的
        public void onHideCustomView() {

            if (xCustomView == null)//不是全屏播放状态
                return;
            //Hidethecustomview.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            //Removethecustomviewfromitscontainer.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            videowebview.setVisibility(View.VISIBLE);

            //Log.i(LOGTAG,"setittowebVew");
        }

        //视频加载添加默认图标
        @Override
        public Bitmap getDefaultVideoPoster() {
            //Log.i(LOGTAG,"hereinongetDefaultVideoPoster");
            if (xdefaltvideo == null) {
                xdefaltvideo = BitmapFactory.decodeResource(
                        getResources(), R.drawable.ic_default);
            }
            return xdefaltvideo;
        }

        //视频加载时进程loading
        @Override
        public View getVideoLoadingProgressView() {
            //Log.i(LOGTAG,"hereinongetVideoLoadingPregressView");

            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater.from(WebPlay2Activity.this);
                xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }

//        //网页标题
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            (WebPlay2Activity.this).setTitle(title);
//        }

        //@Override
//当WebView进度改变时更新窗口进度
        public void onProgressChanged(WebView view, int newProgress) {
// (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS,newProgress*100);

            if (WebPlay2Activity.this.mProgressbar != null) {
                if (newProgress == 100) {
                    WebPlay2Activity.this.mProgressbar.setVisibility(View.GONE);
                } else {
                    if (WebPlay2Activity.this.mProgressbar.getVisibility() == View.GONE) {
                        WebPlay2Activity.this.mProgressbar.setVisibility(View.VISIBLE);
                    }
                    WebPlay2Activity.this.mProgressbar.setProgress(newProgress);
                }
            }
        }
    }

    /**
     * 处理各种通知、请求等事件
     * @author
     */
    public class xWebViewClientent extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("webviewtest", "shouldOverrideUrlLoading:" + url);
            return false;
        }
    }
}
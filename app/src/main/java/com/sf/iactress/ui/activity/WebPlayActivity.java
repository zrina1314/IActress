package com.sf.iactress.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sf.iactress.R;
import com.sf.iactress.base.BaseActivity;
import com.sf.iactress.base.Constants;
import com.sf.sf_utils.LogUtil;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by 花心大萝卜 on 2016/3/28.
 * 用途：
 * 描述：
 */
public class WebPlayActivity extends BaseActivity {
    private static final String TAG = WebPlayActivity.class.getSimpleName();
    private WebView mWebView;
    private ProgressBar mProgressbar;
    private FrameLayout mWebViewLayout;
    private LinearLayout mPanelError;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private String mTitle;
    private String mUrl;
    private String mContentStr;
    private boolean isLoadError = false;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        mWebViewLayout = (FrameLayout) findViewById(R.id.mWebViewLayout);
        mPanelError = (LinearLayout) findViewById(R.id.panel_error);
        mProgressbar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    @Override
    protected void initViewListener() {
    }

    @Override
    protected void process() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mWebView = new WebView(getApplicationContext());
        mWebViewLayout.addView(mWebView);
        mWebViewLayout.setVisibility(View.GONE);
//        AccessibilityManager am = (AccessibilityManager)
//                getSystemService(ACCESSIBILITY_SERVICE);
//
//        List<AccessibilityServiceInfo> listOfServices =
//                am.getEnabledAccessibilityServiceList(
//                        AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
        setWebViewParam();
        Intent intent = getIntent();
        if (intent != null) {
            mTitle = intent.getStringExtra(Constants.Param.WEB_VIEW_TITLE);
            mUrl = intent.getStringExtra(Constants.Param.WEB_VIEW_URL);
        }
        setTitle(mTitle);
        if (!TextUtils.isEmpty(mUrl)) {
            loadWebview(mTitle, mUrl);
        } else {
            mContentStr = intent.getStringExtra(Constants.Param.WEB_VIEW_CONTENT);
            loadContent(mTitle, mContentStr);
        }
    }

    protected void loadWebview(String title, String url) {
        setTitle(title);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }

    protected void loadContent(String title, String data) {
        setTitle(title);
        if (!TextUtils.isEmpty(data)) {
            data = "<html>\n" +
                    " <head></head>\n" +
                    " <body>\n" +
                    "  <div class=\"playbox\"> \n" +
                    "   <iframe width=\"100%\" height=\"600px\" src=\"http://www.xiannvw.com/api/player/?pid=http://player.youku.com/player.php/sid/XMTUxMzQ3OTAxMg==/v.swf\" frameborder=\"0\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"yes\"></iframe> \n" +
                    "  </div>\n" +
                    " </body>\n" +
                    "</html>";            //setTgcToCookie(Content);
            mWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        }
    }

    private void setWebViewParam() {
        mWebView.setInitialScale(55);// 为x%，最小缩放等级
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        // 设置可以支持缩放
//        webSettings.setSupportZoom(true);
//        // 设置出现缩放工具
//        webSettings.setBuiltInZoomControls(true);
//        // 设置默认缩放方式尺寸
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);

        // 让网页自适应屏幕宽度 SINGLE_COLUMN
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);


        WebSettings setting = mWebView.getSettings();

        setting.setJavaScriptEnabled(true);

        setting.setDomStorageEnabled(true);

        setting.setUseWideViewPort(true);

        setting.setLoadWithOverviewMode(true);

//        setting.setUserAgentString(setting.getUserAgentString() + ComplexRes.context.userAgent);


        setting.setDatabaseEnabled(true);

        setting.setGeolocationEnabled(true);

        String dir = mContext.getDir("database", Context.MODE_PRIVATE).getPath();

        setting.setDatabasePath(dir);

        setting.setGeolocationDatabasePath(dir);


        setting.setAppCacheEnabled(true);

        String cacheDir = mContext.getDir("cache", Context.MODE_PRIVATE).getPath();

        setting.setAppCachePath(cacheDir);

        setting.setCacheMode(WebSettings.LOAD_DEFAULT);

        setting.setAppCacheMaxSize(1024 * 1024 * 10);

        setting.setAllowFileAccess(true);


        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        setting.setJavaScriptCanOpenWindowsAutomatically(true);


        setting.setBuiltInZoomControls(true);

        setting.setDisplayZoomControls(false);


        initEvents();

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void initEvents() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (WebPlayActivity.this.mProgressbar != null) {
                    if (newProgress == 100) {
                        WebPlayActivity.this.mProgressbar.setVisibility(View.GONE);
                    } else {
                        if (WebPlayActivity.this.mProgressbar.getVisibility() == View.GONE) {
                            WebPlayActivity.this.mProgressbar.setVisibility(View.VISIBLE);
                        }
                        WebPlayActivity.this.mProgressbar.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                WebPlayActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

            }

            // For Android 3.0+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                WebPlayActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                WebPlayActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), WebPlayActivity.FILECHOOSER_RESULTCODE);

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                LogUtil.getLogger().e(TAG, "开始加载网页：URL=【 " + url + " 】");
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                String dd = "function hideHead() {$(\"header\").hide(); $(\"footer\").hide(); $(\"#topBannerContainer\").hide(); $(\"#container\").hide();}  $(\"#cpro_container\").hide();} ";
//                mWebView.loadUrl("javascript:" + dd);//注入js函数，
//                mWebView.loadUrl("javascript:hideHead()");//调用js函数
                LogUtil.getLogger().e(TAG, "加载网页结束了：URL=【 " + url + " 】");
                onLoadSuccress();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                LogUtil.getLogger().e(TAG, "加载网页失败了：URL=【 " + failingUrl + " 】,ErrorCode=【 " + errorCode + " 】");
                isLoadError = true;
                onLoadSuccress();
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
                        // 时的操作
                        mWebView.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                        WebPlayActivity.this.finish();
                    }
                }
                return false;
            }
        });
    }

    private void onLoadSuccress() {
        if (mWebViewLayout != null)
            mWebViewLayout.setVisibility(isLoadError ? View.GONE : View.VISIBLE);
        if (mPanelError != null)
            mPanelError.setVisibility(isLoadError ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewLayout.removeAllViews();
    }
}
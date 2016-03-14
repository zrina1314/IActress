package com.sf.iactress.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.sf.iactress.R;


/**
 * Created by 花心大萝卜 on 2016/3/1.
 * 用途：欢迎页面
 * 描述：
 */
public class WelcomeActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**全屏设置，隐藏窗口所有装饰**/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);

        //定时进入主页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, 1000);
    }


    private void gotoMainActivity() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        finish();
    }
}

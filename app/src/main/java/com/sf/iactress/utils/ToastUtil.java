package com.sf.iactress.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sf.iactress.R;


public class ToastUtil {
    private static Toast mToast;
    private static Object mSynObj = new Object();

    public static void showToast(final Context context, final String title, final String msg, final int duration) {
        synchronized (mSynObj) {
            try {
                if (mToast == null) {
                    //mToast = Toast.makeText(context, msg, duration);
                    mToast = createToast(context, title, msg);
                } else {
                    //mToast.setText(msg);
                    mToast.setDuration(duration);
                }

                View view = mToast.getView();
                if (TextUtils.isEmpty(title)) {
                    view.findViewById(R.id.tv_toast_title).setVisibility(View.GONE);
                    ((TextView) (view.findViewById(R.id.tv_toast_title))).setText("");
                } else {
                    ((TextView) (view.findViewById(R.id.tv_toast_title))).setText(title);
                    view.findViewById(R.id.tv_toast_title).setVisibility(View.VISIBLE);
                }

                ((TextView) (view.findViewById(R.id.tv_toast_content))).setText(msg);
                mToast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showToast(final Context context, final String title, final String msg) {
        if (!TextUtils.isEmpty(msg)) showToast(context, title, msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final int titleId, final int msgID) {
        showToast(context, context.getString(titleId), context.getString(msgID));
    }

    public static void showToast(final Context context, final String msg) {
        if (!TextUtils.isEmpty(msg)) showToast(context, null, msg);
    }

    public static void showToast(final Context context, final int msgId) {
        showToast(context, context.getString(msgId));
    }


    /**
     * 创建普通Toast
     * @param context
     * @param title
     * @param content
     * @return
     */
    private static Toast createToast(Context context, String title, String content) {
        Toast tempToast;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.view_toast, null);
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_toast_title);
        if (TextUtils.isEmpty(title)) {
            tv_title.setText("");
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }

        TextView tv_content = (TextView) layout.findViewById(R.id.tv_toast_content);
        if (TextUtils.isEmpty(content)) {
            tv_content.setText("");
            tv_content.setVisibility(View.GONE);
        } else {
            tv_content.setText(content);
            tv_content.setVisibility(View.VISIBLE);
        }
        tempToast = new Toast(context.getApplicationContext());
        tempToast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 528);
        tempToast.setDuration(Toast.LENGTH_LONG);
        tempToast.setView(layout);
        return tempToast;
    }
}

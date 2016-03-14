package com.sf.iactress.net.controller;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by 618711 on 2015/5/5.
 */
public class RequestQueueHelper {
    //请求标签
    private final String DEFAULT_REQ_TAG = "Volley_Syt";
    //单例唯一的Application
    private static RequestQueueHelper sInstance;

    // Volley全局请求队列
    private static RequestQueue mRequestQueue;
    private static ArrayList<String> mTags;

    /**
     * 获取单例sInstance
     */
    public static synchronized RequestQueueHelper instance(Context context) {
        if (null == sInstance) {
            sInstance = new RequestQueueHelper();
        }

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return sInstance;
    }

    private RequestQueueHelper() {
    }

    /**
     * 获取Volley请求序列
     *
     * @return RequestQueue Volley序列
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /**
     * 添加请求到Volley序列
     *
     * @param request Volley请求
     * @param reqTag  便于后续可以按指定标签取消请求
     */
    public <T> void addToRequestQueue(Request<T> request, String reqTag) {
        if (mRequestQueue == null)
            return;
        if (mTags == null) mTags = new ArrayList<String>();
        mTags.add(reqTag);
        request.setTag(TextUtils.isEmpty(reqTag) ? DEFAULT_REQ_TAG : reqTag);
        mRequestQueue.add(request);
    }

    /**
     * 按默认标签添加Volley请求
     *
     * @param request Volley请求
     */
    public <T> void addToRequestQueue(Request<T> request) {
        if (mRequestQueue == null)
            return;

        request.setTag(DEFAULT_REQ_TAG);
        mRequestQueue.add(request);
    }

    /**
     * 按指定标签取消所有将要执行或者正要执行的Volley请求
     *
     * @param tag 指定Tag
     */
    public void cancelPendingRequest(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 按默认标签取消所有将要执行或者正要执行的Volley请求
     * <p/>
     * void
     */
    public void cancelPendingRequest() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(DEFAULT_REQ_TAG);
        }
    }

    /**
     * 按不管有没有标签都会把将要执行或者正要执行的Volley请求
     * <p/>
     * void
     */
    public void cancelPendingAllRequest() {
        if (mTags == null || mTags.size() == 0) return;
        for (int i = 0; i < mTags.size(); i++) {
            cancelPendingRequest(mTags.get(i));
        }
        mTags.clear();
    }
}

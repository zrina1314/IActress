package com.sf.iactress.analysis;

import android.text.TextUtils;

import com.sf.iactress.net.controller.ServiceGenerator;
import com.sf.iactress.net.helper.GetKanmxService;
import com.sf.iactress.utils.DateUtil;
import com.sf.iactress.utils.UrlUtil;
import com.sf.sf_utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：图片分析工具
 * 描述：
 */
public class KanmxPictureAnalysis {
    private static final int THREAD_MAX_SIZE = 3;   //最大线程数
    private GetKanmxService getKanmxService;
    private static final String TAG = KanmxPictureAnalysis.class.getSimpleName();
    private String mFirstPageUrl;
    private AnalysisListener mAnalysisListener;
    private Map<String, Call<String>> mCallThreadPool = new HashMap<>(); //线程池
    private List<String> mInProgress = new ArrayList<>();   //处理中的URL
    Map<Integer, String> pages = new HashMap<>();
    Map<String, String> pictures = new HashMap<>();

    /**
     * 第一页数据
     *
     * @param firstPageUrl 第一页URL
     */
    public KanmxPictureAnalysis(String firstPageUrl) {
        this.mFirstPageUrl = firstPageUrl;
    }


    /**
     * 开始爬取数据
     */
    public void startCrawler() {
        LogUtil.getLogger().e(TAG, "开始爬取数据【" + DateUtil.getCurrentTime(DateUtil.DATE_FORMAT_8) + "】");
        crawlerData(mFirstPageUrl);
    }


    /**
     * 根据URL爬去数据
     *
     * @param url
     */
    private void crawlerData(String url) {
        url = UrlUtil.getPath(url);
        mInProgress.add(url);
        LogUtil.getLogger().e(TAG, "当前线程池，总数有：【" + mInProgress.size() + "】");
        LogUtil.getLogger().e(TAG, "当前时间：【" + DateUtil.getCurrentTime(DateUtil.DATE_FORMAT_8) + "】开始加载【" + url + "】");
        if (getKanmxService == null)
            getKanmxService = ServiceGenerator.createService2(GetKanmxService.class);
        Call<String> call = getKanmxService.getPictureList(url);
        mCallThreadPool.put(url, call);
        final String finalUrl = url;
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mInProgress.remove(finalUrl);
                mCallThreadPool.remove(finalUrl);
                String result = response.body();
                if (!TextUtils.isEmpty(result)) {
                    Map<String, Object> tempPictureMap = KanmxAnalysisUtil.getInstance().getAnalysisPicture(result);

                    String picture = (String) tempPictureMap.get("picture");
                    Map<Integer, String> tempPages = (Map<Integer, String>) tempPictureMap.get("pages");
                    //将已经获取到的图片添加到以加载的列表中
                    pictures.put(finalUrl, picture);
                    pages.putAll(tempPages);
                    LogUtil.getLogger().e(TAG, "当前时间：【" + DateUtil.getCurrentTime(DateUtil.DATE_FORMAT_8) + "】加载结束【" + finalUrl + "】,加载的图片结果【" + picture + "】");
                    if (mAnalysisListener != null)
                        mAnalysisListener.analysisProgress(pictures.size());
                    nextCrawlerData();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mCallThreadPool.remove(call);
            }
        });
    }

    private void nextCrawlerData() {
        Iterator iter = pages.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            i++;
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            String val = (String) entry.getValue();
            val = UrlUtil.getPath(val);
            if (!pictures.containsKey(val)) {
                if (mInProgress.contains(val)) continue;
                crawlerData(val);
                if (mCallThreadPool.size() >= THREAD_MAX_SIZE)
                    return;
            }
        }
        if (i == pages.size() && mInProgress.size() == 0) {
            LogUtil.getLogger().e(TAG, "爬取结束数据【" + DateUtil.getCurrentTime(DateUtil.DATE_FORMAT_8) + "】");
            LogUtil.getLogger().e(TAG, "已经全部加载完成了，总共【" + pictures.size() + "】张图片");
            if (mAnalysisListener != null)
                mAnalysisListener.analysisComplete(map2List());
        }
    }

    private List<String> map2List() {
        List<String> pictureList = new ArrayList<>();
        if (pictures != null && pictures.size() > 0) {
            Iterator iter = pictures.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                String val = (String) entry.getValue();
                pictureList.add(val);
            }
        }
        return pictureList;
    }

    public void setAnalysisListener(AnalysisListener analysisListener) {
        this.mAnalysisListener = analysisListener;
    }

    public interface AnalysisListener {
        //void analysisError();
        void analysisComplete(List<String> pictureList);

        void analysisProgress(int PictureSun);
    }

}

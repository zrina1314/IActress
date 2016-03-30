package com.sf.iactress.analysis;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.sf.iactress.base.Constants;
import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.bean.VideoBean;
import com.sf.iactress.net.controller.ServiceGenerator;
import com.sf.iactress.net.helper.GetKanmxService;
import com.sf.iactress.utils.StringUtil;
import com.sf.sf_utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：
 * 描述：
 */
public class XiannvwVideoAnalysisUtil {
    private static final String TAG = KanmxAlbumAnalysisUtil.class.getSimpleName();
    private static XiannvwVideoAnalysisUtil mKanmxAnalysisUtil;

    private XiannvwVideoAnalysisUtil() {

    }

    /**
     * 获取单例
     */
    public static XiannvwVideoAnalysisUtil getInstance() {
        if (mKanmxAnalysisUtil == null) {
            synchronized (XiannvwVideoAnalysisUtil.class) {
                if (mKanmxAnalysisUtil == null)
                    mKanmxAnalysisUtil = new XiannvwVideoAnalysisUtil();
            }
        }
        return mKanmxAnalysisUtil;
    }

    /**
     * 解析相册数据
     * @param htmlStr
     * @return
     */
    public List<VideoBean> getAnalysisVideo(String htmlStr) {
        List<VideoBean> tempAlbumBeanList = new ArrayList<VideoBean>();
        try {
            Document doc = Jsoup.parse(htmlStr);
            Elements divs = doc.select("#sa-comic_show_list");
            Document divcontions = Jsoup.parse(divs.toString());
            Elements element = divcontions.getElementsByTag("li");
            for (Element links : element) {
                String title = "";
                String link = links.select("a").attr("href").trim();
                int id = 0;
                if (!TextUtils.isEmpty(link) && link.contains("/")) {
                    String idStr = link.substring(link.lastIndexOf("/") + 1);
                    if (!TextUtils.isEmpty(idStr) && idStr.contains(".")) {
                        idStr = idStr.substring(0, idStr.indexOf("."));
                        if (!TextUtils.isEmpty(idStr)) {
                            try {
                                id = Integer.parseInt(idStr);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                String url = link;
                String img = links.select("img").attr("src");
                title = links.select("img").attr("alt");
                VideoBean bean = new VideoBean(id, title, url, img);
                tempAlbumBeanList.add(bean);
            }
        } catch (Exception e) {
            LogUtil.getLogger().e(TAG, "解析HTML错误");
            e.printStackTrace();
        }
        return tempAlbumBeanList;
    }


    /**
     * 解析相册数据
     * @param htmlStr
     * @return
     */
    public String getAnalysisVideoSrc(String htmlStr) {
        String tempVideoSrc = null;
        try {
            Document doc = Jsoup.parse(htmlStr);
            //解析到的图片地址
            Element picDivs = doc.getElementById("jc_player");
            Elements iframe = picDivs.select("iframe");
            tempVideoSrc = iframe.attr("src").trim();
            int i = 0;
        } catch (Exception e) {
            LogUtil.getLogger().e(TAG, "解析HTML错误");
            e.printStackTrace();
        }

        return tempVideoSrc;
    }
}
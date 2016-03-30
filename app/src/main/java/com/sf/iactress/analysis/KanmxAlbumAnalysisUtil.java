package com.sf.iactress.analysis;

import android.text.TextUtils;

import com.sf.iactress.bean.AlbumBean;
import com.sf.iactress.utils.StringUtil;
import com.sf.sf_utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：Kanmx数据分析工具
 * 描述：分析性感美女相册/图片数据
 */
public class KanmxAlbumAnalysisUtil {
    private static final String TAG = KanmxAlbumAnalysisUtil.class.getSimpleName();
    private static KanmxAlbumAnalysisUtil mKanmxAnalysisUtil;

    private KanmxAlbumAnalysisUtil() {

    }

    /**
     * 获取单例
     */
    public static KanmxAlbumAnalysisUtil getInstance() {
        if (mKanmxAnalysisUtil == null) {
            synchronized (KanmxAlbumAnalysisUtil.class) {
                if (mKanmxAnalysisUtil == null)
                    mKanmxAnalysisUtil = new KanmxAlbumAnalysisUtil();
            }
        }
        return mKanmxAnalysisUtil;
    }

    /**
     * 解析相册数据
     *
     * @param htmlStr
     * @return
     */
    public List<AlbumBean> getAnalysisAlbum(String htmlStr) {
        List<AlbumBean> tempAlbumBeanList = new ArrayList<AlbumBean>();
        try {
            Document doc = Jsoup.parse(htmlStr);
            Elements divs = doc.select("#img-container");
            Document divcontions = Jsoup.parse(divs.toString());
            Elements element = divcontions.getElementsByClass("img_inner_wrapper");
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
                title = links.select("img").attr("title");
                AlbumBean bean = new AlbumBean(id, title, url, img);
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
     *
     * @param htmlStr
     * @return
     */
    public Map<String, Object> getAnalysisPicture(String htmlStr) {
        Map<String, Object> results = new HashMap<>();
        String picSrc = "";
        Map<Integer, String> pageMap = new HashMap<>();
        try {
            Document doc = Jsoup.parse(htmlStr);
            //解析到的图片地址
            Elements picDivs = doc.getElementsByClass("srcPic");
            picSrc = picDivs.select("img").attr("src");

            //解析页码
            Elements element = doc.getElementsByClass("link_pages");
            Elements elementPages = element.select("a");

            for (Element page : elementPages) {
                String pageStr = page.text();
                if ("上一页".equals(pageStr) || "下一页".equals(pageStr) || !StringUtil.isNumeric(pageStr))
                    continue;
                pageMap.put(Integer.parseInt(pageStr), page.attr("href").trim());
            }
        } catch (Exception e) {
            LogUtil.getLogger().e(TAG, "解析HTML错误");
            e.printStackTrace();
        }
        results.put("picture", picSrc);
        results.put("pages", pageMap);
        return results;
    }
}
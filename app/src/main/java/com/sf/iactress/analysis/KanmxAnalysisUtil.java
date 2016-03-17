package com.sf.iactress.analysis;

import android.text.TextUtils;

import com.sf.iactress.bean.AlbumBean;
import com.sf.sf_utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：Kanmx数据分析工具
 * 描述：分析性感美女相册/图片数据
 */
public class KanmxAnalysisUtil {
    private static final String TAG = KanmxAnalysisUtil.class.getSimpleName();
    private static KanmxAnalysisUtil mKanmxAnalysisUtil;

    private KanmxAnalysisUtil() {

    }

    /**
     * 获取单例
     */
    public static KanmxAnalysisUtil getInstance() {
        if (mKanmxAnalysisUtil == null) {
            synchronized (KanmxAnalysisUtil.class) {
                if (mKanmxAnalysisUtil == null)
                    mKanmxAnalysisUtil = new KanmxAnalysisUtil();
            }
        }
        return mKanmxAnalysisUtil;
    }

//    /**
//     * 获取相册数据
//     *
//     * @param page 页码
//     * @
//     */
//    public void loadListByPage(int page) {
//        try {
//            Document doc = Jsoup.connect(Constans.KANMX_URL).timeout(5000).post();
//            Document content = Jsoup.parse(doc.toString());
//            Elements divs = content.select("#siteNav");
//            Document divcontions = Jsoup.parse(divs.toString());
//            Elements element = divcontions.getElementsByTag("li");
//            Log.d("element", element.toString());
//            for (Element links : element) {
//                String title = links.getElementsByTag("a").text();
//
//                String link = links.select("a").attr("href").replace("/", "").trim();
//                String url = Constans.KANMX_URL + link;
//                ContentValues values = new ContentValues();
//                values.put("Title", title);
//                values.put("Url", url);
//                // usedatabase.insert("Cach", values);
//            }
//
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

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
}
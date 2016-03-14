package com.sf.iactress.analysis;

import android.content.ContentValues;
import android.util.Log;

import com.sf.iactress.base.Constans;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：Kanmx数据分析工具
 * 描述：分析性感美女相册/图片数据
 */
public class KanmxAnalysisUtil {
    private static KanmxAnalysisUtil mKanmxAnalysisUtil;

    private KanmxAnalysisUtil() {

    }

    /** 获取单例 */
    public KanmxAnalysisUtil getInstance() {
        if (mKanmxAnalysisUtil == null) {
            synchronized (this) {
                if (mKanmxAnalysisUtil == null)
                    mKanmxAnalysisUtil = new KanmxAnalysisUtil();
            }
        }
        return mKanmxAnalysisUtil;
    }

    /**
     * 获取相册数据
     *
     * @param page 页码
     * @
     */
    public void loadListByPage(int page) {
        try {
            Document doc = Jsoup.connect(Constans.KANMX_URL).timeout(5000).post();
            Document content = Jsoup.parse(doc.toString());
            Elements divs = content.select("#siteNav");
            Document divcontions = Jsoup.parse(divs.toString());
            Elements element = divcontions.getElementsByTag("li");
            Log.d("element", element.toString());
            for (Element links : element) {
                String title = links.getElementsByTag("a").text();

                String link = links.select("a").attr("href").replace("/", "").trim();
                String url = Constans.KANMX_URL + link;
                ContentValues values = new ContentValues();
                values.put("Title", title);
                values.put("Url", url);
                // usedatabase.insert("Cach", values);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
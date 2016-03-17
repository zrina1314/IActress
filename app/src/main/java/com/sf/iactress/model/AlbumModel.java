package com.sf.iactress.model;

import android.content.Context;
import android.text.TextUtils;

import com.sf.iactress.base.Constans;
import com.sf.iactress.bean.AlbumBean;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 花心大萝卜 on 2016/3/7.
 * 用途：
 * 描述：
 */
public class AlbumModel {
    public Observable<List<AlbumBean>> getUser(final Context context) {
        return Observable.create(new Observable.OnSubscribe<List<AlbumBean>>() {
            @Override
            public void call(Subscriber<? super List<AlbumBean>> subscriber) {
                List<AlbumBean> tempAlbumBeanList = new ArrayList<AlbumBean>();
                // TODO Auto-generated method stub
                try {
                    Connection conn = Jsoup.connect(Constans.KANMX_XINGGAN_URL);
                    // 修改http包中的header,伪装成浏览器进行抓取
                    conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
                    Document doc = conn.timeout(20000).get();

                    Elements divs = doc.select("#img-container");
                    Document divcontions = Jsoup.parse(divs.toString());
                    Elements element = divcontions.getElementsByClass("img_inner_wrapper");
                    for (Element links : element) {
                        String title = "";
//                        Elements titleElement = links.getElementsByClass("title");
//                        if (titleElement != null) {
//                            title = Element.getElementsByTag("a").text();
//                        }
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
                        title =links.select("img").attr("title");
                        AlbumBean bean = new AlbumBean(id, title, url, img);
                        tempAlbumBeanList.add(bean);
                        // usedatabase.insert("Cach", values);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                subscriber.onNext(tempAlbumBeanList);
                subscriber.onCompleted();

//                final User user = null;
//                final AlbumBean user = new AlbumBean("赵日天");
//                if (user == null) {
//                    subscriber.onError(new Exception("User = null"));
//                } else {
//                    subscriber.onNext(user);
//                    subscriber.onCompleted();
//                }
            }
        });
    }
}

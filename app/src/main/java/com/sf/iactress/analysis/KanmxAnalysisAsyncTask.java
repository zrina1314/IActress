package com.sf.iactress.analysis;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import com.sf.iactress.base.Constans;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：
 * 描述：
 */
public class KanmxAnalysisAsyncTask  extends AsyncTask<String, String, String> {
    ProgressDialog bar;
    Document doc;

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        try {
            doc = Jsoup.connect(Constans.KANMX_URL).timeout(5000).post();
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
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
//            Log.d("doc", doc.toString().trim());
        bar.dismiss();
//        ListItemAdapter adapter = new ListItemAdapter(context, usedatabase.getlist());
//        listmenu.setAdapter(adapter);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

        // bar = new ProgressDialog(context);
//        bar.setMessage("正在加载数据····");
//        bar.setIndeterminate(false);
//        bar.setCancelable(false);
//        bar.show();
    }

}
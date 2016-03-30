package com.sf.iactress.net.helper;

import com.sf.iactress.base.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 花心大萝卜 on 2016/3/29.
 * 用途：仙女屋数据抓取服务
 * 描述：
 */
public interface GetXianNvWService {
    @GET("xinggan/{page}.html")
    Call<String> getVideoList(@Path("page") int page);

    @GET("{url}")
    Call<String> getVideoDetail(@Path("url") String url);

    @GET(Constants.M_XIANNVW_URL)
    Call<String> getVideoSrc(@Path("id") String id);
}

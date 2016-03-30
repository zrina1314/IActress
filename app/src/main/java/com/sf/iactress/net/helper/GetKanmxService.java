package com.sf.iactress.net.helper;

import com.sf.iactress.base.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：用于请求服务器
 * 描述：
 */
public interface GetKanmxService {
    @GET("AAA/xinggan/index_{page}.html")
    Call<String> getAlbumList(@Path("page") int page);

    @GET(Constants.KANMX_HOME_PAGE_URL)
    Call<String> getAlbumHomePageList();

    @GET("{url}")
    Call<String> getPictureList(@Path("url") String url);
}

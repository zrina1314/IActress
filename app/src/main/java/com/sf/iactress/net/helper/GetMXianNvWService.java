package com.sf.iactress.net.helper;

import com.sf.iactress.base.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 花心大萝卜 on 2016/3/29.
 * 用途：仙女屋数据抓取服务
 * 描述：
 */
public interface GetMXianNvWService {
    @GET(Constants.M_XIANNVW_URL)
    Call<String> getVideoSrc(@Query("action") String action, @Query("id") String id);
}

package com.sf.iactress.net.controller;

import com.sf.iactress.base.Constans;
import com.sf.iactress.net.controller.gson.GsonConverterFactory;
import com.sf.iactress.net.controller.string.StringConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by 花心大萝卜 on 2016/3/17.
 * 用途：
 * 描述：
 */
public class ServiceGenerator {
    //    public static final String API_BASE_URL = "http://192.168.0.106/";
//    public static final String API_BASE_URL2 = "http://192.168.6.134/";
    public static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static Retrofit.Builder mBuilder = new Retrofit.Builder()
            .baseUrl(Constans.KANMX_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder mStringBuilder = new Retrofit.Builder()
            .baseUrl(Constans.KANMX_URL)
            .addConverterFactory(StringConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService2(Class<S> serviceClass) {
        Retrofit retrofit = mStringBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }
}

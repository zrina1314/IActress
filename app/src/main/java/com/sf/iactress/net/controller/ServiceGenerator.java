package com.sf.iactress.net.controller;

import com.sf.iactress.base.Constants;
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
            .baseUrl(Constants.KANMX_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder mStringBuilder = new Retrofit.Builder()
            .baseUrl(Constants.KANMX_URL)
            .addConverterFactory(StringConverterFactory.create());

    private static Retrofit.Builder mXianNvWBuilder = new Retrofit.Builder()
            .baseUrl(Constants.XIANNVW_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder mXianNvWStringBuilder = new Retrofit.Builder()
            .baseUrl(Constants.XIANNVW_URL)
            .addConverterFactory(StringConverterFactory.create());
    private static Retrofit.Builder mMXianNvWBuilder = new Retrofit.Builder()
            .baseUrl(Constants.M_XIANNVW_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder mMXianNvWStringBuilder = new Retrofit.Builder()
            .baseUrl(Constants.M_XIANNVW_URL)
            .addConverterFactory(StringConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createService2(Class<S> serviceClass) {
        Retrofit retrofit = mStringBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createXianNvWService(Class<S> serviceClass) {
        Retrofit retrofit = mXianNvWBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createXianNvWService2(Class<S> serviceClass) {
        Retrofit retrofit = mXianNvWStringBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createMXianNvWService(Class<S> serviceClass) {
        Retrofit retrofit = mMXianNvWBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createMXianNvWService2(Class<S> serviceClass) {
        Retrofit retrofit = mMXianNvWStringBuilder.client(sOkHttpClient).build();
        return retrofit.create(serviceClass);
    }
}

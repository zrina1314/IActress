package com.sf.iactress.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by 花心大萝卜 on 2016/3/18.
 * 用途：
 * 描述：
 */
public class UrlUtil {

    public static String getPath(String url) {
        String path = url;
        try {
            path = new URI(url).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

}

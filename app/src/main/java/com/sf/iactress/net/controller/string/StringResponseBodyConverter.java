package com.sf.iactress.net.controller.string;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by jiang.xu on 2016/3/1.
 */
final class StringResponseBodyConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            String tempResult = null;
            byte[] bytes = value.bytes();
            String content = new String(bytes);
            // 默认为utf-8编码
            String charset = "utf-8";
            // 匹配<head></head>之间，出现在<meta>标签中的字符编码
            Pattern pattern = Pattern.compile("<head>([\\s\\S]*?)<meta([\\s\\S]*?)charset\\s*=(\")?(.*?)\"");
            Matcher matcher = pattern.matcher(content.toLowerCase());
            if (matcher.find()) {
                charset = matcher.group(4);
                if (charset.equals("gb2312")) {
                    byte[] gbkBytes = new String(bytes, "gbk").getBytes();
                    tempResult = new String(gbkBytes, "utf-8");
                    return tempResult;
                }
            }
            // 将目标字符编码转化为utf-8编码
            String temp = new String(bytes, charset);
            byte[] contentData = temp.getBytes("utf-8");
            tempResult = new String(contentData);
            return tempResult;
        } catch (Exception e) {
            String s = value.string();
            return s;
        } finally {
            value.close();
        }
    }
}
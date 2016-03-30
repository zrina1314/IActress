package com.sf.iactress.base;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：
 * 描述：
 */
public class Constants {
    public final static String KANMX_URL = "http://www.84420.com/";

    public final static String KANMX_HOME_PAGE_URL = "http://www.84420.com/index.html";

    public final static String XIANNVW_URL = "http://www.xiannvw.com/";
    public final static String M_XIANNVW_URL = "http://m.xiannvw.com/";
//    public final static String M_XIANNVW_URL = "http://m.xiannvw.com/?action=article&id={id}";
    /** 瀑布流的列数 */
    public final static int COLUM_COUNT = 2;

    /** 页面跳转参数定义 */
    public interface Param {
        /** 网页标题 */
        String WEB_VIEW_TITLE = "web_view_title";
        /** 网页URL */
        String WEB_VIEW_URL = "web_view_url";
        /** 网页内容 */
        String WEB_VIEW_CONTENT = "web_view_content";
    }
}

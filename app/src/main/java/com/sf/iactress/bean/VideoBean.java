package com.sf.iactress.bean;

import com.sf.iactress.base.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 花心大萝卜 on 2016/3/6.
 * 用途：相册实体
 * 描述：
 */
public class VideoBean {
    private int id;
    private String name;
    private String cover;
    private String link;

    public VideoBean() {
    }

    public VideoBean(int id, String name, String link, String cover) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.cover = cover;
    }

    /**
     * 获取ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 设置ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取名称
     * @return
     */
    public String getName() {
        if (name.indexOf("xiannvw.com") != -1 || name.indexOf("xianvw.com") != -1)
            return name.replaceAll("xiannvw.com", "").replaceAll("xianvw.com", "");
        return name;
    }

    /**
     * 设置名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取封面
     * @return
     */
    public String getCover() {
        return Constants.XIANNVW_URL + cover;
    }

    /**
     * 设置封面
     * @param cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "id=【" + id + "】，name=【" + name + "】，link=【" + link + "】，cover=【" + cover + "】";
    }
}

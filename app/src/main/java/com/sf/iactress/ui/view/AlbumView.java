package com.sf.iactress.ui.view;

import com.sf.iactress.bean.AlbumBean;

import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/8.
 * 用途：
 * 描述：
 */
public interface AlbumView {
    void updateView(List<AlbumBean> user);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);
}

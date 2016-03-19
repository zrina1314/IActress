package com.sf.iactress.ui.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sf.iactress.R;
import com.sf.iactress.ui.fragment.PictureFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：
 * 描述：
 */
public class PictureAdapter extends FragmentPagerAdapter {
    private List<String> mUrls = new ArrayList<>();
    Map<String, Fragment> fragmentList = new HashMap<>();
    public PictureAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        this.mUrls = urls;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList.containsKey(mUrls.get(position))) {
            return fragmentList.get(mUrls.get(position));
        } else {
            PictureFragment fragment = new PictureFragment(mUrls.get(position));
            fragmentList.put(mUrls.get(position), fragment);
            return fragment;
        }
    }
}
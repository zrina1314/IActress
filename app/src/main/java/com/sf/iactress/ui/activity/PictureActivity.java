package com.sf.iactress.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.sf.iactress.R;
import com.sf.iactress.base.BaseFragmentActivity;
import com.sf.iactress.ui.adapter.PictureAdapter;
import com.sf.iactress.ui.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/19.
 * 用途：
 * 描述：
 */
public class PictureActivity extends BaseFragmentActivity {
    private static final String ISLOCKED_ARG = "isLocked";
    private HackyViewPager mViewPager;
    private String defaultPicture;
    private HashMap<Integer, String> pageUrls;
    private List<String> urls = new ArrayList<>();
    private PictureAdapter mAdapter;

    @Override
    protected int initLayoutID() {
        return R.layout.activity_picture;
    }

    @Override
    protected void initView() {
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mAdapter = new PictureAdapter(getSupportFragmentManager(), urls);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initViewListener() {

    }

    @Override
    protected void process() {
        List<String> tempPictures = getIntent().getStringArrayListExtra("pictures");
        if (tempPictures != null && tempPictures.size() > 0) {
            urls.addAll(tempPictures);
            mAdapter.notifyDataSetChanged();
        } else {
            defaultPicture = getIntent().getStringExtra("picture");
            pageUrls = (HashMap<Integer, String>) getIntent().getSerializableExtra("pages");
            urls.add(defaultPicture);
            mAdapter.notifyDataSetChanged();
            map2List();
        }
    }

    private boolean isViewPagerActive() {
        return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }

    private void map2List() {
//        urls.clear();
//        if (pageUrls != null && pageUrls.size() > 0) {
//            Iterator iter = pageUrls.entrySet().iterator();
//            while (iter.hasNext()) {
//                Map.Entry entry = (Map.Entry) iter.next();
//                Object key = entry.getKey();
//                String val = (String) entry.getValue();
//                urls.add(val);
//            }
//        }
        // mAdapter.notifyDataSetChanged();
    }
}

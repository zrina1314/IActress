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
    private DisplayImageOptions options;

    public PictureAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        this.mUrls = urls;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_default)
                .showImageForEmptyUri(R.drawable.ic_default)
                .showImageOnFail(R.drawable.ic_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
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

//    @Override
//    public View instantiateItem(ViewGroup container, int position) {
//        PhotoView photoView = new PhotoView(container.getContext());
//        String url = Constans.KANMX_URL + mUrls.get(position);
//        ImageLoader.getInstance().displayImage(url, photoView, options);
//        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
////
////        if (container == null) {
////            container = View.inflate(mContext, R.layout.adapter_store_item, null);
////        }
////
////        StoreItemView storeItemView = ViewHolder.get(convertView, R.id.store_info);
////        storeItemView.loadData2View(getItem(i), mHideStoreSend);
////        storeItemView.setNearMarker(i==0);
//
//
//
//
//        return photoView;
//    }


//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }

}
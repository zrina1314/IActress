package com.sf.iactress.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.sf.iactress.R;

/**
 * Created by 花心大萝卜 on 2016/3/20.
 * 用途：
 * 描述：
 */
public class ImageOptionUtil {

    public static DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
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

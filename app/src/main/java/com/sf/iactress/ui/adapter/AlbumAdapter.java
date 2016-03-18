package com.sf.iactress.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sf.iactress.R;
import com.sf.iactress.base.BaseRecyclerViewAdapter;
import com.sf.iactress.bean.AlbumBean;
import com.sf.sf_utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/7.
 * 用途：相册数据适配器
 * 描述：
 */
public class AlbumAdapter extends BaseRecyclerViewAdapter<AlbumBean> {
    private static final String TAG = AlbumAdapter.class.getSimpleName();
    private DisplayImageOptions options;
    private int mItemWidth = 0;

    public AlbumAdapter(Context context, int itemWidth) {
        super(context);
        this.mItemWidth = itemWidth;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_album_item, viewGroup, false);
        //将创建的View注册点击事件
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        onItemClickListener(viewHolder, position);
        ImageLoader.getInstance().displayImage(getList().get(position).getCover(), viewHolder.imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view, int imageWidth, int imageHeight) {
                if (imageWidth != 0 && imageHeight != 0) {
                    int newImageHeight = (int) (((mItemWidth * 1f) / (imageWidth * 1f)) * (imageHeight * 1f));
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mItemWidth, newImageHeight);
                    view.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
        viewHolder.textView.setText(getList().get(position).getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_album_cover);
            textView = (TextView) itemView.findViewById(R.id.tv_album_title);
        }
    }
}

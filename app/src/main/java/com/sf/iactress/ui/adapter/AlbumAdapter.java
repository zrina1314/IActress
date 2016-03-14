package com.sf.iactress.ui.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.sf.iactress.R;
import com.sf.iactress.bean.AlbumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 花心大萝卜 on 2016/3/7.
 * 用途：相册数据适配器
 * 描述：
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MasonryView> {
    private List<AlbumBean> mAlbumBeans;
    private DisplayImageOptions options;

    public AlbumAdapter(List<AlbumBean> list) {
        if (list == null)
            list = new ArrayList<>();
        mAlbumBeans = list;
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
                .showImageOnLoading(R.drawable.ic_default)
                .showImageForEmptyUri(R.drawable.ic_default)
                .showImageOnFail(R.drawable.ic_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_album_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        ImageLoader.getInstance().displayImage(mAlbumBeans.get(position).getCover(), masonryView.imageView, options);
        masonryView.textView.setText(mAlbumBeans.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mAlbumBeans.size();
    }

    public static class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_album_cover);
            textView = (TextView) itemView.findViewById(R.id.tv_album_title);
        }
    }

    public void addAlbum(AlbumBean albumBean) {
        mAlbumBeans.add(albumBean);
        notifyItemInserted(mAlbumBeans.size() - 1);
    }

}

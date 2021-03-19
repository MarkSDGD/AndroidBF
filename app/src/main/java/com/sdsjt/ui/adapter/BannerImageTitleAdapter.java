package com.sdsjt.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdsjt.R;
import com.sdsjt.model.response.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，图片+标题
 */

public class BannerImageTitleAdapter extends BannerAdapter<BannerBean.DataBean, BannerImageTitleAdapter.ImageTitleHolder> {

    private final Context context;

    public BannerImageTitleAdapter(Context context, List<BannerBean.DataBean> mDatas) {
        super(mDatas);
        this.context=context;
    }

    @Override
    public ImageTitleHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image_title, parent, false));
    }

    @Override
    public void onBindView(ImageTitleHolder holder, BannerBean.DataBean data, int position, int size) {
       // GlideUtils.load(context,data.getImagePath(), holder.imageView,R.mipmap.index_banner_placeholder);
        holder.imageView.setImageResource(data.getImageRes());
        holder.title.setText(data.getTitle());
    }

    public static class ImageTitleHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;

        public ImageTitleHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            title = view.findViewById(R.id.bannerTitle);
        }
    }
}

package com.sdsjt.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdsjt.R;
import com.sdsjt.model.entity.Model;

import java.util.List;

/**
 * Created by Mark on 2021/2/3.
 * <p>Copyright 2021 ZTZQ.</p>
 */
public class CollapseAdapter extends BaseQuickAdapter<Model, BaseViewHolder> {

    public CollapseAdapter(@Nullable List<Model> data) {
        super(R.layout.item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Model item) {
      //可链式调用赋值
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.subtitle, item.getContent());
        ImageView img=(ImageView) helper.getView(R.id.img);
        img.setImageResource(R.drawable.login);
        // 设置图片
     //   Glide.with(mContext).load(item.getImgUrl()).into((ImageView) helper.getView(R.id.iv_img));

    }
}

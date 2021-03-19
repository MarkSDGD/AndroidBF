package com.sdsjt.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdsjt.R;
import com.sdsjt.model.response.ArticleBean;
import com.sdsjt.utils.GlideUtils;

import java.util.List;



public class TemplateAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> {

    public TemplateAdapter(@Nullable List<ArticleBean.DataBean.DatasBean> data) {
        super(R.layout.item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        //可链式调用赋值
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.subtitle, item.getAuthor());
        ImageView img=(ImageView) helper.getView(R.id.img);
        //img.setImageResource(R.drawable.login);
        GlideUtils.load(mContext,item.getEnvelopePic(),img,R.drawable.login);


    }
}

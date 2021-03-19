package com.sdsjt.ui.iview;


import com.sdsjt.model.response.ArticleBean;


public interface lTemplateListView {

    //void onGetNewsListSuccess(List<News> newList, String tipInfo);
    void onGetChannelDataSuccess(ArticleBean channelBean, boolean isRefresh);
    void  onError();

}

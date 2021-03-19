package com.sdsjt.ui.iview;


import com.sdsjt.model.response.SearchSuggestionBean;

import java.util.List;



public interface lSearchView {

    void  onError();
    void onGetSearchHotWordSuccess(List<String> hotList);
    void onGetSearchSuggestionSuccess(List<SearchSuggestionBean.DataBean> suggestList);
}

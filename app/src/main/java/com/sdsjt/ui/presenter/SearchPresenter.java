package com.sdsjt.ui.presenter;


import com.sdsjt.model.response.SearchRecommendBean;
import com.sdsjt.model.response.SearchSuggestionBean;
import com.sdsjt.ui.base.BasePresenter;
import com.sdsjt.ui.iview.lSearchView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;



public class SearchPresenter extends BasePresenter<lSearchView> {

    private long lastTime;

    public SearchPresenter(lSearchView view) {
        super(view);
    }


    public void getSearchHotword(){


        addSubscription(mApiService.getSearchRecommend(), new DisposableObserver<SearchRecommendBean>() {
            @Override
            public void onComplete() {
                KLog.e("MARK","onComplete");
            }

            @Override
            public void onError(Throwable e) {
                KLog.e("MARK","onError");
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(SearchRecommendBean response) {
                KLog.e("MARK","onNext");
                List<SearchRecommendBean.DataBean.SuggestWordListBean> suggest_word_list = response.getData().getSuggest_word_list();
                List<String> hotList = new ArrayList<>();
                for (int i = 0; i < suggest_word_list.size(); i++) {
                    if (suggest_word_list.get(i).getType().equals("recom")) {
                        hotList.add(suggest_word_list.get(i).getWord());
                    }
                }

                mView.onGetSearchHotWordSuccess(hotList);

            }
        });
    }

    public void getSearchSuggestion(String keyword){


        addSubscription(mApiService.getSearchSuggestion(keyword), new DisposableObserver<SearchSuggestionBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(SearchSuggestionBean response) {
                List<SearchSuggestionBean.DataBean> suggestList =response.getData();
                mView.onGetSearchSuggestionSuccess(suggestList);
            }
        });
    }
}

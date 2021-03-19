package com.sdsjt.ui.presenter;


import com.sdsjt.model.response.ArticleBean;
import com.sdsjt.ui.base.BasePresenter;
import com.sdsjt.ui.iview.lTemplateListView;
import com.socks.library.KLog;

import io.reactivex.observers.DisposableObserver;



public class TemplateListPresenter extends BasePresenter<lTemplateListView> {

    public TemplateListPresenter(lTemplateListView view) {
        super(view);
    }

    public void getArticleList(int channelCode , int page, boolean isRefresh){
        addSubscription(mApiService.getArticleList(channelCode,page), new DisposableObserver<ArticleBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(ArticleBean response) {
                try{
                    if(response.getErrorCode()==0){
                        mView.onGetChannelDataSuccess(response,isRefresh);
                    } else{
                        mView.onError();
                        KLog.e("get channel data failed !");
                    }
                 }catch (Exception e){
                    e.printStackTrace();
                 }finally {

                 }

            }
        });
    }
}

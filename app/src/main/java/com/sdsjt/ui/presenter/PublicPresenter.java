package com.sdsjt.ui.presenter;


import com.sdsjt.model.response.PublicNumber;
import com.sdsjt.ui.base.BasePresenter;
import com.sdsjt.ui.iview.lPublicListView;
import com.socks.library.KLog;

import io.reactivex.observers.DisposableObserver;


public class PublicPresenter extends BasePresenter<lPublicListView> {

    public PublicPresenter(lPublicListView view) {
        super(view);
    }

    public void getPublicChannelData(){
        addSubscription(mApiService.getPublicChannels(), new DisposableObserver<PublicNumber>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(PublicNumber response) {
                try{
                    if(response.getErrorCode()==0){
                        mView.onGetChannelDataSuccess(response);
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

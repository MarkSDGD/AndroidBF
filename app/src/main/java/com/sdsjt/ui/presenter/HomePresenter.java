package com.sdsjt.ui.presenter;


import com.sdsjt.model.response.BannerBean;
import com.sdsjt.ui.base.BasePresenter;
import com.sdsjt.ui.iview.lHomeView;
import com.socks.library.KLog;

import io.reactivex.observers.DisposableObserver;


public class HomePresenter extends BasePresenter<lHomeView> {

    public HomePresenter(lHomeView view) {
        super(view);
    }

    public void getBannerData(){
        addSubscription(mApiService.getBannerData(), new DisposableObserver<BannerBean>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                KLog.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onNext(BannerBean response) {
                try{
                    if(response.getErrorCode()==0){
                        mView.onGetBannerDataSuccess(response);
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

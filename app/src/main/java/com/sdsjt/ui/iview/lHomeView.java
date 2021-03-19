package com.sdsjt.ui.iview;


import com.sdsjt.model.response.BannerBean;


public interface lHomeView {

    void onGetBannerDataSuccess(BannerBean channelBean);
    void  onError();

}

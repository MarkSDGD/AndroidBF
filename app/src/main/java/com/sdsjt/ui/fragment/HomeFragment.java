package com.sdsjt.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sdsjt.R;
import com.sdsjt.model.entity.Model;
import com.sdsjt.model.response.BannerBean;
import com.sdsjt.ui.activity.SearchActivity;
import com.sdsjt.ui.adapter.HomeAdapter;
import com.sdsjt.ui.adapter.BannerImageTitleAdapter;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.iview.lHomeView;
import com.sdsjt.ui.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseFragment<HomePresenter> implements lHomeView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.search_edit)
    EditText searchEdit;

    private HomeAdapter mOneAdapter;
    private List<Model> mItemList = new ArrayList<>();
    private List<BannerBean.DataBean> bannerDataList =new ArrayList<>();
    int[] imgRes = new int[]{R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4,R.drawable.banner5,R.drawable.banner6,R.drawable.banner7,R.drawable.banner8};
    private int bannerHeight;

    private Banner mBanner;
    @Override
    protected HomePresenter createPresenter() {

        return new HomePresenter(this);
    }

    @Override
    protected int provideContentViewId() {

        return R.layout.fragment_home;
    }

    @Override
    public void initData() {

        for (int i = 0; i < 15; i++) {
            Model model = new Model();
            model.setImgUrl("");
            model.setTitle("?????????" + i + "?????????");
            model.setContent("???" + i + "?????????");
            mItemList.add(model);
        }
        for (int i = 0; i < 8; i++) {
            BannerBean.DataBean dataBean = new BannerBean.DataBean();
            dataBean.setImageRes(imgRes[i]);
            dataBean.setTitle("?????????????????????????????????"+"--???"+(i+1)+"???");
            bannerDataList.add(dataBean);
        }
        initBanner(bannerDataList);

    }



    @Override
    public void initListener() {
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                if (totalDy <= bannerHeight) {
                    float alpha = (float) totalDy / bannerHeight;
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mActivity, R.color.colorPrimary), alpha));
                } else {
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mActivity, R.color.colorPrimary), 1));
                }
            }
        });

        searchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void initView(View rootView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);

        mOneAdapter = new HomeAdapter(mItemList);
        mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        View headImage = LayoutInflater.from(mActivity).inflate(R.layout.header_image, (ViewGroup) mRv.getParent(), false);
        mOneAdapter.addHeaderView(headImage);

        View headView = LayoutInflater.from(mActivity).inflate(R.layout.header_banner, (ViewGroup) mRv.getParent(), false);
        mBanner = headView.findViewById(R.id.banner);
        mOneAdapter.addHeaderView(headView);

        ViewGroup.LayoutParams headImageParams = headImage.getLayoutParams();
        ViewGroup.LayoutParams titleBarParams = mToolbar.getLayoutParams();
        bannerHeight = headImageParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(mActivity);
        mRv.setAdapter(mOneAdapter);

    }

    @Override
    public void loadData() {
        //  mPresenter.getBannerData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onGetBannerDataSuccess(BannerBean bannerBean) {
        List<BannerBean.DataBean> bannerDataList =bannerBean.getData();

        initBanner(bannerDataList);
    }

    private void initBanner(List<BannerBean.DataBean> bannerDataList) {
        mBanner.addBannerLifecycleObserver(getActivity());
        mBanner.setAdapter(new BannerImageTitleAdapter(getActivity(),bannerDataList));
        mBanner.setIndicator(new CircleIndicator(getActivity()));
        mBanner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
        //??????????????????
        mBanner.setBannerGalleryEffect(18, 10);
        mBanner.addPageTransformer(new AlphaPageTransformer());
        //??????????????????????????????true
        mBanner.isAutoLoop(true);
        //??????????????????
        mBanner.setLoopTime(2500);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }

        });
        //banner?????????????????????????????????????????????
        mBanner.start();
    }

    @Override
    public void onError() {

    }
}

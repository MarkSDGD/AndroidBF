package com.sdsjt.ui.fragment;

import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.mark.transform.AccordionTransformer;
import com.sdsjt.R;
import com.sdsjt.model.response.BannerBean;
import com.sdsjt.model.response.PublicNumber;
import com.sdsjt.ui.adapter.BannerImageTitleAdapter;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.iview.lPublicListView;
import com.sdsjt.ui.presenter.PublicPresenter;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicFragment extends BaseFragment<PublicPresenter> implements lPublicListView {

    @BindView(R.id.tab_channel)
    SlidingTabLayout mTabChannel;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;
    @BindView(R.id.banner)
    Banner mBanner;
    private List<PublicNumber.DataBean> mChannelList = new ArrayList<>();
    private SparseArray<TemplateFragment> fragmentSparseArray = new SparseArray<>();

    private List<BannerBean.DataBean> bannerDataList =new ArrayList<>();
    int[] imgRes = new int[]{R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4,R.drawable.banner5,R.drawable.banner6,R.drawable.banner7,R.drawable.banner8};

    @Override
    protected PublicPresenter createPresenter() {
        return new PublicPresenter(this);
    }

    @Override
    protected int provideContentViewId() {

        return R.layout.fragment_public;
    }

    @Override
    public void initView(View rootView) {

    }

    private void initChannelFragments() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TemplateFragment tempFragment = fragmentSparseArray.get(position);
                if (tempFragment == null) {
                    tempFragment= TemplateFragment.newInstance( mChannelList.get(position).getId());
                    fragmentSparseArray.put(position, tempFragment);
                }
                return tempFragment;
            }

            @Override
            public int getCount() {
                return mChannelList == null ? 0 : mChannelList.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Html.fromHtml(mChannelList.get(position).getName());
            }
        });
        mTabChannel.setViewPager(mViewPager);
       // mTabChannel.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initData() {
        for (int i = 0; i < 8; i++) {
            BannerBean.DataBean dataBean = new BannerBean.DataBean();
            dataBean.setImageRes(imgRes[i]);
            dataBean.setTitle("世界这么大，我想去看看"+"--第"+(i+1)+"张");
            bannerDataList.add(dataBean);
        }
        initBanner(bannerDataList);
        mPresenter.getPublicChannelData();
    }

    @Override
    public void loadData() {
        //KLog.i("MARK","VideoFragment loadData");
      //mPresenter.getPublicChannelData();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onGetChannelDataSuccess(PublicNumber channelBean) {
        mChannelList = channelBean.getData();
        initChannelFragments();
    }

    @Override
    public void onError() {

    }
    private void initBanner(List<BannerBean.DataBean> bannerDataList) {
        mBanner.addBannerLifecycleObserver(getActivity());
        mBanner.setAdapter(new BannerImageTitleAdapter(getActivity(),bannerDataList));
        mBanner.setIndicator(new CircleIndicator(getActivity()));
        mBanner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);

        //添加画廊效果
       // mBanner.setBannerGalleryEffect(18, 10);
       // mBanner.addPageTransformer(new CompositePageTransformer());
        mBanner.setPageTransformer(new AccordionTransformer());
        //设置自动轮播，默认为true
        mBanner.isAutoLoop(true);
        //设置轮播时间
        mBanner.setLoopTime(2500);

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }

        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }
}

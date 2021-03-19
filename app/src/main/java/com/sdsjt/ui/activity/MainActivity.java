package com.sdsjt.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.mark.uibox.NoScrollViewPager;
import com.sdsjt.R;
import com.sdsjt.model.entity.TabEntity;
import com.sdsjt.ui.adapter.MainTabAdapter;
import com.sdsjt.ui.base.BaseActivity;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.base.BasePresenter;
import com.sdsjt.ui.fragment.CollapseFragment;
import com.sdsjt.ui.fragment.HomeFragment;
import com.sdsjt.ui.fragment.MineFragment;
import com.sdsjt.ui.fragment.PublicFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_content_main)
    NoScrollViewPager mVpContent;
    @BindView(R.id.bottom_tab_layout)
    CommonTabLayout bottomTabLayout;
    private List<BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;
    private int CurrentTabPosition = 0;
    private String CurrentChannelCode = "";
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<CustomTabEntity>(Arrays.asList(

            new TabEntity("首页", "4101000101", R.mipmap.tab_home_selected, R.mipmap.tab_home_normal, new HomeFragment()),
            new TabEntity("公众号", "4102000101", R.mipmap.tab_video_selected, R.mipmap.tab_video_normal, new PublicFragment()),
            new TabEntity("折叠", "4103000101", R.mipmap.tab_micro_selected, R.mipmap.tab_micro_normal, new CollapseFragment()),
            new TabEntity("我的", "4104000101", R.mipmap.tab_me_selected, R.mipmap.tab_me_normal, new MineFragment()))
    );
    FragmentStatePagerAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();

        mVpContent.setOffscreenPageLimit(mTabEntities.size());
        adapter = new Pageradapter(getSupportFragmentManager());
        mVpContent.setAdapter(adapter);
        bottomTabLayout.setTabData(mTabEntities);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {


        bottomTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int selectedPosition) {
                mVpContent.setCurrentItem(selectedPosition, false);
                CurrentTabPosition = selectedPosition;
                switch (selectedPosition) {
                    case 0:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).navigationBarColor(R.color.colorPrimary).init();
                        break;
                    case 1:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).navigationBarColor(R.color.status_color_red).init();
                        break;
                    case 2:

                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).navigationBarColor(R.color.common_grey).init();
                        break;
                    case 3:
                        ImmersionBar.with(MainActivity.this).statusBarDarkFont(false).navigationBarColor(R.color.white).init();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabReselect(int selectedPosition) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private class Pageradapter extends FragmentStatePagerAdapter {

        public Pageradapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return ((TabEntity) (mTabEntities.get(arg0))).getFragment();
        }

        @Override
        public int getCount() {
            return mTabEntities.size();
        }

    }

}

package com.sdsjt.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.mark.uibox.TipView;
import com.mark.uibox.powerfulrecyclerview.PowerfulRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdsjt.R;
import com.sdsjt.config.Constant;

import com.sdsjt.model.response.ArticleBean;
import com.sdsjt.ui.adapter.TemplateAdapter;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.iview.lTemplateListView;
import com.sdsjt.ui.presenter.TemplateListPresenter;
import com.sdsjt.utils.ListUtils;
import com.sdsjt.utils.NetWorkUtils;
import com.sdsjt.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ezy.ui.layout.LoadingLayout;


public class TemplateFragment extends BaseFragment<TemplateListPresenter> implements lTemplateListView {

    private static final String TAG = TemplateFragment.class.getSimpleName();

    @BindView(R.id.tip_view)
    TipView mTipView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.loading)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;

    private int mChannelCode;


    private List<ArticleBean.DataBean.DatasBean> mNewsList = new ArrayList<>();
    protected BaseQuickAdapter mNewsAdapter;


    private Gson mGson = new Gson();


    List<ArticleBean.DataBean.DatasBean> newsList = new ArrayList<>();

    private int currentPage = 1;

    public static TemplateFragment newInstance(int channelCode) {
        TemplateFragment newsFragment = new TemplateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.CHANNEL_CODE, channelCode);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    protected TemplateListPresenter createPresenter() {

        return new TemplateListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {

        return R.layout.fragment_template;
    }


    @Override
    public void initView(View rootView) {

        mRvNews.setLayoutManager(new LinearLayoutManager(mActivity));
        mRvNews.setHasFixedSize(true);
        mNewsAdapter = new TemplateAdapter(mNewsList);
        mRvNews.setAdapter(mNewsAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mChannelCode = getArguments().getInt(Constant.CHANNEL_CODE);
        }
    }

    @Override
    public void initData() {
        //此处可以先加载缓存
        mNewsList.clear();
        mNewsList.addAll(newsList);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!NetWorkUtils.isNetworkAvailable(mActivity)) {

                    mRefreshLayout.finishRefresh();
                    //网络不可用弹出提示
                    mTipView.show();
                    return;
                }
                currentPage = 1;
                mPresenter.getArticleList(mChannelCode, currentPage, true);
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!NetWorkUtils.isNetworkAvailable(mActivity)) {

                    mRefreshLayout.finishLoadMore();

                    //网络不可用弹出提示
                    mTipView.show();
                    return;
                }
                currentPage += 1;
                mPresenter.getArticleList(mChannelCode, currentPage, false);
            }
        });
    }

    @Override
    protected void loadData() {
        mLoadingLayout.showLoading();
        // mRefreshLayout.autoRefresh();
        mPresenter.getArticleList(mChannelCode, currentPage, true);
    }


    @Override
    public void onGetChannelDataSuccess(ArticleBean articleBean, boolean isRefresh) {

        //如果是第一次获取数据
        //显示内容
        if (ListUtils.isEmpty(mNewsList)) {
            if (ListUtils.isEmpty(articleBean.getData().getDatas())) {
                //获取不到数据,显示空布局
                mLoadingLayout.showEmpty();
                return;
            }
        }
        mLoadingLayout.showContent();//显示内容

        if (ListUtils.isEmpty(articleBean.getData().getDatas())) {
            //已经获取不到数据
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }
        if (isRefresh) {
            mRefreshLayout.finishRefresh();// 加载完毕后在 UI 线程结束下拉刷新
            mNewsList.clear();
            mNewsList.addAll(articleBean.getData().getDatas());
            mNewsAdapter.replaceData(articleBean.getData().getDatas());

        } else {
            mRefreshLayout.finishLoadMore();
            mNewsList.addAll(articleBean.getData().getDatas());
            mNewsAdapter.addData(articleBean.getData().getDatas());
        }
        //此处保存到数据库


    }


    @Override
    public void onError() {
        //收起刷新
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadMore();
        mTipView.show();//弹出提示
        if (ListUtils.isEmpty(mNewsList)) {
            //如果一开始进入没有数据
            // mLoadingLayout.showError();//显示重试的布局
            mLoadingLayout.showEmpty();
        }

    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}

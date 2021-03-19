package com.sdsjt.ui.activity;


import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayout;
import com.sdsjt.R;
import com.sdsjt.config.Constant;
import com.sdsjt.database.dao.SearchHistoryDao;
import com.sdsjt.model.response.SearchHistoryBean;
import com.sdsjt.model.response.SearchSuggestionBean;
import com.sdsjt.ui.adapter.SearchResultPagerAdapter;
import com.sdsjt.ui.adapter.SearchHistoryAdapter;
import com.sdsjt.ui.adapter.SearchSuggestionAdapter;
import com.sdsjt.ui.base.BaseActivity;
import com.sdsjt.ui.iview.lSearchView;
import com.sdsjt.ui.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class SearchActivity extends BaseActivity<SearchPresenter> implements lSearchView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.history_list)
    ListView historyList;
    @BindView(R.id.hotword_layout)
    LinearLayout hotwordLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.result_layout)
    LinearLayout resultLayout;
    @BindView(R.id.suggestion_list)
    ListView suggestionList;
    private SearchSuggestionAdapter suggestionAdapter;
    private SearchView searchView;
    private SearchHistoryAdapter historyAdapter;
    private String[] titles = new String[]{"视频", "图片", "文字"};
    private SearchHistoryDao dao = new SearchHistoryDao();
    private boolean isBeginSearch;

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        //ImmersionBar.with(this).statusBarColor(R.color.news_tag_border_blue).init();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        flexboxLayout.setFlexDirection(FlexDirection.ROW);
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);

        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        suggestionAdapter = new SearchSuggestionAdapter(this, -1);
        suggestionList.setAdapter(suggestionAdapter);

        // 搜索历史
        historyList = findViewById(R.id.history_list);
        historyAdapter = new SearchHistoryAdapter(this, -1);
        historyList.setAdapter(historyAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        // searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView = (SearchView) item.getActionView();

       /* // 关联检索配置与 SearchActivity
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchActivity.class));
        searchView.setSearchableInfo(searchableInfo);*/



        /*------------------ SearchView有三种默认展开搜索框的设置方式，区别如下： ------------------*/
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
        searchView.setIconified(false);
        //设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        // searchView.setIconifiedByDefault(false);
        //设置搜索框直接展开显示。左侧有无放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        // searchView.onActionViewExpanded();

        //设置最大宽度
        //  searchView.setMaxWidth(500);
         //设置是否显示搜索框展开时的提交按钮
        //   searchView.setSubmitButtonEnabled(true);
        //设置输入框提示语
        searchView.setQueryHint("搜索");
        // searchView.setBackgroundResource(R.drawable.shape_search_bg);

        // 去掉搜索框默认的下划线
        LinearLayout search_plate = searchView.findViewById(R.id.search_plate);
        LinearLayout submit_area = searchView.findViewById(R.id.submit_area);
        search_plate.setBackground(null);
        submit_area.setBackground(null);

       // 设置搜索文字样式
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.common_text_color));
        searchEditText.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        //searchEditText.setBackgroundColor(Color.WHITE);
        setOnQuenyTextChangeListener();

        return super.onCreateOptionsMenu(menu);
    }

    private void setOnQuenyTextChangeListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String keyWord) {
                Log.i("MARK","onQueryTextSubmit keyWord=="+keyWord);
                searchView.clearFocus();
                initSearchLayout(keyWord);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (dao.queryisExist(keyWord)) {
                            dao.update(keyWord);
                        } else {
                            dao.add(keyWord);
                        }
                    }
                }).start();
                return false;
            }

            //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if(isBeginSearch){ //真正搜索的时候不需要再触发以下逻辑
                    isBeginSearch = false;
                    return false;
                }
                Log.i("MARK","onQueryTextChange newText=="+newText);
                if (!TextUtils.isEmpty(newText)) {
                    getSearchSuggest(newText);
                    hotwordLayout.setVisibility(View.GONE);
                    resultLayout.setVisibility(View.GONE);
                    suggestionList.setVisibility(View.VISIBLE);
                } else {
                    getSearchHistory();
                    if (hotwordLayout.getVisibility() != View.VISIBLE) {
                        hotwordLayout.setVisibility(View.VISIBLE);
                    }
                    if (resultLayout.getVisibility() != View.GONE) {
                        resultLayout.setVisibility(View.GONE);
                    }
                    if (suggestionList.getVisibility() != View.GONE) {
                        suggestionList.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });


    }

    private void initSearchLayout(String query) {
        hotwordLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        suggestionList.setVisibility(View.GONE);
        List<Fragment> fragmentList = new ArrayList<>();
        // for (int i = 1; i < titles.length + 1; i++) {
        // fragmentList.add(SearchResultFragment.newInstance(query, i + ""));
        // }
        SearchResultPagerAdapter pagerAdapter = new SearchResultPagerAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());

    }

    private void getSearchHotWord() {
        mPresenter.getSearchHotword();

    }

    private void getSearchHistory() {
        List<SearchHistoryBean> list = dao.queryAll();
        historyAdapter.updateDataSource(list);

    }

    private void getSearchSuggest(String keyWord) {
        mPresenter.getSearchSuggestion(keyWord);

    }


    @Override
    public void initData() {
        getSearchHotWord();
        getSearchHistory();
    }

    @Override
    public void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealBackPressed();
            }
        });

        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyWord = suggestionAdapter.getItem(position).getKeyword();
                beginSearch(keyWord);
            }
        });

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keyWord = historyAdapter.getItem(position).getKeyWord();
                beginSearch(keyWord);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    public void onError() {

    }

    @Override
    public void onGetSearchHotWordSuccess(List<String> hotList) {
        for (int i = 0; i < hotList.size(); i++) {
            final TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_search_sug_text, flexboxLayout, false);
            final String keyWord = hotList.get(i);
            int color = Constant.TAG_COLORS[i % Constant.TAG_COLORS.length];
            tv.setText(keyWord);
            tv.setBackgroundColor(color);
            tv.setTextColor(Color.WHITE);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    beginSearch(keyWord);
                }
            });
            flexboxLayout.addView(tv);
            if (i == 7) {
                return;
            }
        }
    }

    private void beginSearch(String keyWord) {
        searchView.clearFocus();
        isBeginSearch=true;
        searchView.setQuery(keyWord, true);
    }

    @Override
    public void onGetSearchSuggestionSuccess(List<SearchSuggestionBean.DataBean> suggestList) {
        suggestionAdapter.updateDataSource(suggestList);
    }


    @OnClick({R.id.tv_refresh, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                flexboxLayout.removeAllViews();
                getSearchHotWord();
                break;
            case R.id.tv_clear:
                new AlertDialog.Builder(this).setMessage(R.string.delete_all_search_history).setPositiveButton(R.string.button_enter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteAll();
                        getSearchHistory();
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dealBackPressed();

    }

    private void dealBackPressed() {
        if (suggestionList.getVisibility() != View.GONE) {
            // 关闭搜索建议
            suggestionList.setVisibility(View.GONE);
            hotwordLayout.setVisibility(View.VISIBLE);
        } else if (resultLayout.getVisibility() != View.GONE) {
            // 关闭搜索结果
            searchView.setQuery("", false);
            searchView.clearFocus();
            resultLayout.setVisibility(View.GONE);
            hotwordLayout.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }
}

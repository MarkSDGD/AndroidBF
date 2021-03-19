package com.sdsjt.ui.fragment;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdsjt.R;
import com.sdsjt.model.entity.Model;
import com.sdsjt.ui.adapter.CollapseAdapter;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;


public class CollapseFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<Model> appList;
    private CollapseAdapter adapter;
    @Override
    protected BasePresenter createPresenter() {
        //KLog.i("createPresenter");
        return null;
    }

    @Override
    protected int provideContentViewId() {
        //KLog.i("provideContentViewId");
        return  R.layout.fragment_collapse;
    }

    @Override
    public void initView(View rootView) {
        //KLog.i("initView");
        //初始化RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        //模拟的数据（实际开发中一般是从网络获取的）
        appList = new ArrayList<>();
        //创建布局管理
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        //创建适配器
        adapter = new CollapseAdapter(appList);
        adapter.bindToRecyclerView(recyclerView);
        //给RecyclerView设置适配器
        // recyclerView.setAdapter(adapter);
        //条目点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getContext(), "点击了第" + (position + 1) + "条条目", Toast.LENGTH_SHORT).show();
            }
          
        });

    }

    @Override
    public void initData() {
        //KLog.i("initData");
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Model model;
                for (int i = 0; i < 15; i++) {
                    model = new Model();
                    model.setImgUrl("");
                    model.setTitle("我是第" + i + "条标题");
                    model.setContent("第" + i + "条内容");
                    appList.add(model);
                }
                adapter.setNewData(appList);
            }
        },0);
    }

    @Override
    public void initListener() {
        //KLog.i("initListener");
    }

    @Override
    public void loadData() {
        //KLog.i("loadData");
    }


}

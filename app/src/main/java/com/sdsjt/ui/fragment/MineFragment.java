package com.sdsjt.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;

import com.sdsjt.R;
import com.sdsjt.ui.base.BaseFragment;
import com.sdsjt.ui.base.BasePresenter;

import butterknife.BindView;


public class MineFragment extends BaseFragment {

    @BindView(R.id.project_layout)
    RelativeLayout project;
    @BindView(R.id.csdn_layout)
    RelativeLayout csdn;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {

        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://blog.csdn.net/nhce12931"));
                startActivity(intent);
            }
        });
        csdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://blog.csdn.net/nhce12931"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {
        //KLog.i("loadData");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

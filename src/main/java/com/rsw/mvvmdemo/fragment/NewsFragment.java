package com.rsw.mvvmdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.activity.ZixunWebViewActivity;
import com.rsw.mvvmdemo.adapter.ProviderDailyAdapter;
import com.rsw.mvvmdemo.base.MvvmLazyFragment;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.databinding.HomeFragmentNewsBinding;
import com.rsw.mvvmdemo.utils.LeoUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class NewsFragment extends MvvmLazyFragment<NewsViewModel, HomeFragmentNewsBinding> {

    private ProviderDailyAdapter adapter;
    public int type;
    private ArrayList<YinshiBean.ListBean> list = new ArrayList<>();
    private int page = 1;
    private int size = 15;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_news;
    }


    @Override
    protected void getData() {
        super.getData();
        page = 1;
        getCollects(null);
    }

    @Override
    protected void initNativeView() {
        initView();
    }

    @Override
    protected void setListener() {
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getCollects(ParamsBuilder.build().isShowDialog(false));
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCollects(ParamsBuilder.build().isShowDialog(false));
            }
        });
    }

    /**
     * @param
     * @return
     * @description
     * @author rtrs-renshiwu
     * @time 2020/10/13 14:08
     */
    private void getCollects(ParamsBuilder paramsBuilder) {
        mViewModel.getYunfuyinshiList(page, size, type, paramsBuilder).observe(this, stringResource -> {
            stringResource.handler(new OnCallback<List<YinshiBean.ListBean>>() {
                @Override
                public void onSuccess(List<YinshiBean.ListBean> data) {
                    if (data.size() > 0) {
                        if (page == 1) {
                            list.clear();
                        }
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    } else {
                        binding.refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    LeoUtils.checkEmpty(list.size(), binding.empty);
                }
            }, binding.refreshLayout);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            type = getArguments().getInt("type");
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    private void initView() {
        // 确定Item的改变不会影响RecyclerView的宽高
        binding.rvDailyView.setHasFixedSize(true);
        binding.rvDailyView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderDailyAdapter(getContext(), list);
        binding.rvDailyView.setAdapter(adapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
        binding.refreshLayout.setEnableLoadMore(true);
        binding.refreshLayout
                .setRefreshFooter(new ClassicsFooter(getContext()));
        adapter.setOnItemClickLitener(new ProviderDailyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                YinshiBean.ListBean bean = list.get(position);
                Intent intent = new Intent(getContext(), ZixunWebViewActivity.class);
                intent.putExtra("item", bean);
                startActivity(intent);
            }
        });
    }
}

package com.rsw.mvvmdemo.fragment;

import android.content.Intent;
import android.view.View;

import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.activity.ZixunWebViewActivity;
import com.rsw.mvvmdemo.adapter.ProviderDailyAdapter;
import com.rsw.mvvmdemo.adapter.ProviderNominateAdapter;
import com.rsw.mvvmdemo.base.MvvmLazyFragment;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.customview.RecyclerAdapterWithHF;
import com.rsw.mvvmdemo.databinding.HomeFragmentNominateBinding;
import com.rsw.mvvmdemo.databinding.HomeItemBannerViewBinding;
import com.rsw.mvvmdemo.holder.NetBannerHolder;
import com.rsw.mvvmdemo.utils.DateUtils;
import com.rsw.mvvmdemo.utils.LeoUtils;
import com.rsw.mvvmdemo.viewmodel.NominateViewModel;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.holder.HolderCreator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * package name：com.example.betterhealthmvvm.main.fragment
 * author : 任先森
 * creation date: 2020/9/15 15:48
 * description ：首页推荐
 */
public class NominateFragment
        extends MvvmLazyFragment<NominateViewModel, HomeFragmentNominateBinding> {
    private ProviderNominateAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    private int size = 15;
    private int page = 1;
    private HomeItemBannerViewBinding bannerBinding;
    private ArrayList<YinshiBean.ListBean> list = new ArrayList<>();
    public ArrayList<YinshiBean.ListBean> banners = new ArrayList<>();

    public static NominateFragment newInstance() {
        NominateFragment fragment = new NominateFragment();
        return fragment;
    }

    @Override
    protected void getData() {
        super.getData();
        page = 1;
        getYinshiList(null);
        getBanners();
    }

    private void getBanners() {
        mViewModel.getBanners(5, ParamsBuilder.build().isShowDialog(false)).observe(this, stringResource -> {
            stringResource.handler(new OnCallback<List<YinshiBean.ListBean>>() {
                @Override
                public void onSuccess(List<YinshiBean.ListBean> data) {
                    if (data.size() > 0) {
                        banners.clear();
                        banners.addAll(data);
                        bannerBinding.bvTop.setHolderCreator(new HolderCreator<NetBannerHolder>() {
                            @Override
                            public NetBannerHolder createViewHolder() {
                                return new NetBannerHolder();
                            }
                        }).setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                                .create(banners);
                        bannerBinding.bvTop.setVisibility(View.VISIBLE);
                    } else {
                        bannerBinding.bvTop.setVisibility(View.GONE);
                    }
                }
            }, null);
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_nominate;
    }

    @Override
    protected void initNativeView() {
    }

    @Override
    protected void setListener() {
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getYinshiList(ParamsBuilder.build().isShowDialog(false));
                getBanners();
            }
        });
        binding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getYinshiList(ParamsBuilder.build().isShowDialog(false));
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }

    private void initView() {
        binding.rvNominateView.setHasFixedSize(true);
        binding.rvNominateView
                .setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProviderNominateAdapter(getContext(), list);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mAdapter.addHeader(getHeaderView());
        binding.rvNominateView.setAdapter(mAdapter);
        binding.refreshLayout
                .setRefreshHeader(new ClassicsHeader(getContext()));
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


    private void getYinshiList(ParamsBuilder paramsBuilder) {
        mViewModel.getYunfuyinshiListBykeyWord(page, size, DateUtils.getSeason(), paramsBuilder).observe(this, stringResource -> {
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

    /**
     * 配置头部banner
     */
    private View getHeaderView() {
        bannerBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.home_item_banner_view,
                binding.rvNominateView,
                false);
        View view = bannerBinding.getRoot();
        bannerBinding.bvTop.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                System.out.println("onPageClick==========" + position);
            }
        });
        return view;
    }
}


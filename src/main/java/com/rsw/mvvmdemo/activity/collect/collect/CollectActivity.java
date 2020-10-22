package com.rsw.mvvmdemo.activity.collect.collect;

import android.view.View;

import com.lihang.nbadapter.BaseAdapter;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.activity.collect.collect.adapter.CollectAdapter;
import com.rsw.mvvmdemo.base.BaseActivity;
import com.rsw.mvvmdemo.bean.HomeBean;
import com.rsw.mvvmdemo.bean.basebean.EventBusBean;
import com.rsw.mvvmdemo.bean.basebean.HomeFatherBean;
import com.rsw.mvvmdemo.bean.basebean.ParamsBuilder;
import com.rsw.mvvmdemo.databinding.ActivityCollectBinding;
import com.rsw.mvvmdemo.utils.LeoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


/**
 * Created by leo
 * on 2019/11/14.
 */
public class CollectActivity extends BaseActivity<CollectViewModel, ActivityCollectBinding> implements BaseAdapter.OnItemClickListener<HomeBean> {
    private CollectAdapter adapter;
    private int curPage = 0;
    private ArrayList<HomeBean> homeBeans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initNativeView() {
        getCollects(curPage, null);
        adapter = new CollectAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.setDataList(homeBeans);
        binding.recyclerView.setAdapter(adapter);
    }


    private void getCollects(int curPage, ParamsBuilder paramsBuilder) {
        mViewModel.getCollectArticles(curPage, paramsBuilder).observe(this, stringResource -> {
            stringResource.handler(new OnCallback<HomeFatherBean>() {
                @Override
                public void onSuccess(HomeFatherBean data) {
                    if (data.getDatas().size() > 0) {
                        if (curPage == 0) {
                            homeBeans.clear();
                        }
                        homeBeans.addAll(data.getDatas());
                        adapter.notifyItemRangeChanged(homeBeans.size() - data.getDatas().size(), data.getDatas().size());
                    } else {
                        binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    LeoUtils.checkEmpty(homeBeans.size(), binding.empty);
                }
            }, binding.smartRefreshLayout);
        });
    }

    @Override
    protected void setListener() {
        binding.leoTitleBar.bar_left_btn.setOnClickListener(this);
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            curPage = 0;
            getCollects(curPage, ParamsBuilder.build().isShowDialog(false));
        });

        binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            curPage++;
            getCollects(curPage, ParamsBuilder.build().isShowDialog(false));
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_btn:
                finish();
                break;
            case R.id.image_zan:
                HomeBean homeBean = (HomeBean) v.getTag(R.id.image_zan);
                int position = (int) v.getTag(R.id.linear_);
                mViewModel.unCollectByMe(homeBean.getId(), homeBean.getOriginId()).observe(this, resource -> {
                    resource.handler(new OnCallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                            homeBeans.remove(homeBean);
                            EventBus.getDefault().post(new EventBusBean(2, homeBean.getChapterId()));
                            LeoUtils.checkEmpty(homeBeans.size(), binding.empty);
                        }
                    });
                });
                break;
        }
    }

    @Override
    public void onItemClick(HomeBean item, int position) {
//        startActivity(ActivityUtils.build(this, WebActivity.class)
//                .putExtra("url", item.getLink()));
    }
}

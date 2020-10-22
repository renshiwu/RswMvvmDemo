package com.rsw.mvvmdemo.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.adapter.HomeFragmentPageAdapter;
import com.rsw.mvvmdemo.base.MvvmLazyFragment;
import com.rsw.mvvmdemo.databinding.HomeFragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by RSW
 * on 2020/10/12
 */
public class HomeFragment extends MvvmLazyFragment<HomeFragmentViewModel, HomeFragmentHomeBinding> {

    private HomeFragmentPageAdapter pageAdapter;
    private String[] tables = {"推荐", "百科", "母婴", "瘦身", "情感", "心理", "女性", "男性", "旅游", "社会", "时尚"};

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    protected int getContentViewId() {
        return R.layout.home_fragment_home;
    }


    @Override
    protected void initNativeView() {
        initView();
        List<Fragment> fragments = new ArrayList<>();
        NominateFragment newsFragment = new NominateFragment();
        fragments.add(newsFragment);
        for (int i = 1; i < 11; i++) {
            NewsFragment fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i + 1);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        pageAdapter.setData(fragments, tables);
        binding.vpHomeContent.setCurrentItem(0);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    private void initView() {
        pageAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.vpHomeContent.setAdapter(pageAdapter);
        binding.tabLayout
                .setupWithViewPager(binding.vpHomeContent);
        binding.vpHomeContent.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(
                        binding.tabLayout));
        binding.tabLayout
                .addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        binding.vpHomeContent
                                .setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
        binding.ivNetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}


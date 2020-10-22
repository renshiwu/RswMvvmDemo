package com.rsw.mvvmdemo.activity.main;

import android.view.View;

import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.adapter.MainPageAdapter;
import com.rsw.mvvmdemo.base.BaseActivity;
import com.rsw.mvvmdemo.databinding.ActivityMainBinding;
import com.rsw.mvvmdemo.fragment.HomeFragment;
import com.rsw.mvvmdemo.fragment.NewsFragment;
import com.rsw.mvvmdemo.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import me.majiajie.pagerbottomtabstrip.NavigationController;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    private List<Fragment> fragments;

    private MainPageAdapter adapter;

    private NavigationController mNavigationController;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initNativeView() {
        initView();
        initFragment();
    }

    @Override
    protected void setListener() {

    }


    private void initView() {
        mNavigationController = binding.bottomView.material()
                .addItem(R.drawable.main_home,
                        "首页",
                        ColorUtil.getColor(this, R.color.main_bottom_check_color))
                .addItem(R.drawable.main_community,
                        "菜谱",
                        ColorUtil.getColor(this, R.color.main_bottom_check_color))
                .addItem(R.drawable.main_notify,
                        "消息",
                        ColorUtil.getColor(this, R.color.main_bottom_check_color))
                .addItem(R.drawable.main_user,
                        "我的",
                        ColorUtil.getColor(this, R.color.main_bottom_check_color))
                .setDefaultColor(
                        ColorUtil.getColor(this, R.color.main_bottom_default_color))
                .enableAnimateLayoutChanges()
                .build();
        mNavigationController.setHasMessage(3, true);
        mNavigationController.setMessageNumber(2, 1);
        adapter = new MainPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        binding.cvContentView.setOffscreenPageLimit(1);
        binding.cvContentView.setAdapter(adapter);
        mNavigationController.setupWithViewPager(binding.cvContentView);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        //通过ARouter 获取其他组件提供的fragment
        Fragment homeFragment = HomeFragment.newInstance();
        Fragment communityFragment = NewsFragment.newInstance();
        Fragment moreFragment = NewsFragment.newInstance();
        Fragment userFragment = NewsFragment.newInstance();
        fragments.add(homeFragment);
        fragments.add(communityFragment);
        fragments.add(moreFragment);
        fragments.add(userFragment);
        adapter.setData(fragments);
    }

    @Override
    public void onClick(View v) {

    }
}
package com.rsw.mvvmdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 *  @author rtrs-renshiwu
 *  @time 2020/9/15  14:32
 *  @describe
 */
public class HomeFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private String[] tables;

    public HomeFragmentPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    public void setData(List<Fragment> fragment, String[] tables) {
        this.tables = tables;
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.addAll(fragment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragments != null && fragments.size() > 0) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null && fragments.size() > 0) {
            return fragments.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tables[position];
    }
}

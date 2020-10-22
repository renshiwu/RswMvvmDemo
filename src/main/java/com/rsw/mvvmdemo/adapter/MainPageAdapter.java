package com.rsw.mvvmdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**rtrs-renshiwu
 *  @author
 *  @time 2020/9/14  14:37
 *  @describe
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments ;
    public MainPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setData(List<Fragment> data){
        if (fragments == null){
            fragments = new ArrayList<>();
        }
        fragments.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragments != null && fragments.size() > 0){
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null && fragments.size() > 0){
            return fragments.size();
        }
        return 0;
    }
}

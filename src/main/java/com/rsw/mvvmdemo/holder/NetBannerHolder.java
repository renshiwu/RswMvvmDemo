package com.rsw.mvvmdemo.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.zhpan.bannerview.holder.ViewHolder;

/**
 * Created by RSW
 * on 2020/10/13
 */
public class NetBannerHolder implements ViewHolder<YinshiBean.ListBean> {
    @Override
    public int getLayoutId() {
        return R.layout.home_item_banner_item_view;
    }

    @Override
    public void onBind(View itemView, YinshiBean.ListBean data, int position, int size) {
        ImageView imageView = itemView.findViewById(R.id.banner_bg);
        Glide.with(imageView.getContext())
                .load(data.getImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                .into(imageView);
        System.out.println("data========" + data);
    }
}

package com.rsw.mvvmdemo.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rsw.mvvmdemo.R;
import com.youth.banner.loader.ImageLoader;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
//                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .transition(withCrossFade())
                .centerCrop().into(imageView);

        //这里有点奇怪4.9的，不主动倒包。渐变加载动画，可以传参int durtion.默认300

    }
}

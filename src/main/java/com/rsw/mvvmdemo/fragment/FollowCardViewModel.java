package com.rsw.mvvmdemo.fragment;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.base.BaseViewModel;
import com.rsw.mvvmdemo.base.RepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

/**
 * Created by RSW
 * on 2020/10/12
 */

public class FollowCardViewModel extends BaseViewModel<RepositoryImpl> {

    public int count;
    public String description;
    public String img;
    public String linkUrl;
    public String name;
    public String imgPath;
    public String htmlPath;
    public String food;
    public String content;
    public String img_path;
    public String link_url;
    public int class_id;
    public int id;
    public int class_two_id;
    public String image;
    public String pick;
    public String time;
    public List<String> images = new ArrayList<>();
    public FollowCardViewModel(@NonNull Application application) {
        super(application);
    }

    @BindingAdapter("showImage1")
    public static void showImage1(ImageView imageView, String image) {
        Glide.with(imageView.getContext()).load(image).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img)
                .into(imageView);

    }

    @BindingAdapter("showImage2")
    public static void showImage2(ImageView imageView, String image) {
        Glide.with(imageView.getContext()).load(image).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img)
                .into(imageView);

    }

    @BindingAdapter("showImage3")
    public static void showImage3(ImageView imageView, String image) {
        Glide.with(imageView.getContext()).load(image).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img)
                .into(imageView);

    }


    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
package com.rsw.mvvmdemo.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rsw.mvvmdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

/**
 * 应用模块:adapter
 * <p>
 * 类描述: 自定义BindingAdapter
 * <p>
 *
 * @author darryrzhoong
 * @since 2020-01-28
 */
public class CommonBindingAdapters {


    @BindingAdapter({"newName"})
    public static void setNewName(TextView tv, String oName) {
        tv.setText(oName);
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                    .into(imageView);
        }

    }

    @BindingAdapter("showImg")
    public static void showImg(ImageView imageView, String img) {
        Glide.with(imageView.getContext()).load(img)
                .transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img)
                .into(imageView);
    }

    @BindingAdapter("showImage")
    public static void showImage(ImageView imageView, String image) {
        Glide.with(imageView.getContext()).load(image).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img)
                .into(imageView);

    }

    @BindingAdapter("imageBgUrl")
    public static void loadBgImage(final ViewGroup viewGroup, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(viewGroup.getContext())
                    .asBitmap()
                    .load(url)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(resource);
                            viewGroup.setBackground(drawable);
                        }
                    });
        }
    }

    @BindingAdapter("imageWrapUrl")
    public static void loadWrapImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                    .into(imageView);
        }

    }

    @BindingAdapter("imageCircleUrl")
    public static void loadCircleImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        }

    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}

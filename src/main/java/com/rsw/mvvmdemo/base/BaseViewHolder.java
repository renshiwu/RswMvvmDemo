package com.rsw.mvvmdemo.base;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by RSW
 * on 2020/10/12
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

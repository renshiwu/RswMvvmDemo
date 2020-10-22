package com.rsw.mvvmdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.base.BaseViewHolder;
import com.rsw.mvvmdemo.bean.YinshiBean;
import com.rsw.mvvmdemo.common.Constant;
import com.rsw.mvvmdemo.databinding.HomeItemFollowCardViewBinding;
import com.rsw.mvvmdemo.utils.DateUtils;
import com.rsw.mvvmdemo.utils.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author rtrs-renshiwu
 * @time 2020/9/14  15:06
 * @describe adapter 提供者
 */
public class ProviderDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<YinshiBean.ListBean> mDatas;

    public ProviderDailyAdapter(Context context, List<YinshiBean.ListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        HomeItemFollowCardViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.home_item_follow_card_view, viewGroup, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
        HomeItemFollowCardViewBinding binding = (HomeItemFollowCardViewBinding) baseViewHolder.binding;
        YinshiBean.ListBean entity = mDatas.get(position);
        if (null == entity.getImage() || !entity.getImage().contains("@rsw@")) {
            binding.layOne.setVisibility(View.VISIBLE);
            binding.layThree.setVisibility(View.GONE);
            String imgUrl = "";
            if (null != entity.getImg()) {
                imgUrl = entity.getImg();
            } else {
                imgUrl = entity.getImgPath();
                imgUrl = Constant.BASE_IMG_URL + imgUrl;
            }
            Glide.with(context).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(binding.ivImage); //设置占位图，在加载之前显示,.thumbnail(0.1f) thumbnail设置缩略图
            binding.tvTitle.setText(entity.getName());
            if (null != entity.getDescription() && !"".equals(entity.getDescription())) {
                binding.tvContent.setVisibility(View.VISIBLE);
                binding.layTip.setVisibility(View.GONE);
                binding.tvContent.setText(entity.getDescription());
            } else {
                binding.layTip.setVisibility(View.VISIBLE);
                binding.tvContent.setVisibility(View.GONE);
                if (null != entity.getPick()) {
                    binding.tvPick.setVisibility(View.VISIBLE);
                    binding.tvPick.setText(Util.ellpiseText(entity.getPick()));
                } else {
                    binding.tvPick.setVisibility(View.GONE);
                }
                if (null != entity.getTime() && !"".equals(entity.getTime())) {
                    binding.tvDate.setVisibility(View.VISIBLE);
                    binding.tvDate.setText(DateUtils.stampToDate(entity.getTime()));
                } else {
                    binding.tvDate.setVisibility(View.GONE);
                }
            }
        } else {
            binding.layOne.setVisibility(View.GONE);
            binding.layThree.setVisibility(View.VISIBLE);
            String imgUrl = entity.getImage();
            String[] strs = imgUrl.split("@rsw@");
            if (strs.length >= 3) {
                Glide.with(context).load(strs[0]).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(binding.ivImg1);
                Glide.with(context).load(strs[1]).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(binding.ivImg2);
                Glide.with(context).load(strs[2]).transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.default_img).error(R.drawable.default_img).into(binding.ivImg3);
            }
            binding.tvTitle2.setText(entity.getName());
            if (null != entity.getPick()) {
                binding.tvAuthor.setVisibility(View.VISIBLE);
                binding.tvAuthor.setText(entity.getPick());
            } else {
                binding.tvAuthor.setVisibility(View.GONE);
            }
            if (null != entity.getTime() && !"".equals(entity.getTime())) {
                binding.tvTime.setVisibility(View.VISIBLE);
                binding.tvTime.setText(DateUtils.stampToDate(entity.getTime()));
            } else {
                binding.tvTime.setVisibility(View.GONE);
            }
        }
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView,
                            position);
                }
            });
        }
        //区分是banner还是列表
//    @Override
//    public int getItemViewType(int position) {
//        if (dataList.get(position) instanceof FollowCardViewModel) {
//            return NominateItemType.FOLLOW_CARD_VIEW;
//        } else if (dataList.get(position) instanceof SingleTitleViewModel) {
//            return NominateItemType.SINGLE_TITLE_VIEW;
//        }
//        return -1;
//    }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}

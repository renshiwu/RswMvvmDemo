package com.rsw.mvvmdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonSyntaxException;
import com.rsw.mvvmdemo.R;
import com.rsw.mvvmdemo.bean.basebean.Resource;
import com.rsw.mvvmdemo.customview.CustomProgress;
import com.rsw.mvvmdemo.utils.ToastUtils;
import com.rsw.mvvmdemo.utils.networks.NetWorkUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by RSW
 * on 2020/10/12
 * 支持懒加载的fragment
 */
public abstract class MvvmLazyFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends RxFragment {

    protected View rootView = null;

    /**
     * 布局是否创建完成
     */
    protected boolean isViewCreated = false;

    /**
     * 当前可见状态
     */
    protected boolean currentVisibleState = false;

    /**
     * 是否第一次可见
     */
    protected boolean mIsFirstVisible = true;

    //获取当前activity布局文件
    protected abstract int getContentViewId();

    // 初始化view
    protected abstract void initNativeView();

    //所有监听放这里
    protected abstract void setListener();


    protected VM mViewModel;
    protected VDB binding;

    private CustomProgress dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
    }

    /**
     * 初始化参数
     */
    protected void initParameters() {
        createViewModel();
    }

    protected void getData() {

    }

    public Context getContext() {
        return getActivity();
    }

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(getActivity(), "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("throwable==========" + throwable);
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_network_error));
                return;
            }
            if (throwable instanceof ConnectException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_error));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_timeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtils.showToast("数据解析出错");
            } else {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_empty_error));
            }
        }

        @Override
        public void onFailure(String msg) {
            ToastUtils.showToast(msg);
        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int precent, long total) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            binding =
                    DataBindingUtil.inflate(inflater, getContentViewId(), container, false);
            rootView = binding.getRoot();
            setListener();
        }
        isViewCreated = true;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 初始化的时候,判断当前Fragment可见状态
        // isHidden在使用FragmentTransaction的show/hidden时会调用,可见返回的是false
        if (!isHidden() && getUserVisibleHint()) {
            // 可见状态,进行事件分发
            dispatchUserVisibleHint(true);
        }
        initNativeView();
    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) new ViewModelProvider(this).get(modelClass);
            mViewModel.setObjectLifecycleTransformer(bindToLifecycle());
        }
    }

    /**
     * 修改Fragment的可见性 setUserVisibleHint 被调用有两种情况：
     * 1）在切换tab的时候，会先于所有fragment的其他生命周期，先调用这个函数，可以看log 2)
     * 对于之前已经调用过setUserVisibleHint方法的fragment后，让fragment从可见到不可见之间状态的变化
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.println("setUserVisibleHint: " + isVisibleToUser);
        // 对于情况1）不予处理，用 isViewCreated 进行判断，如果isViewCreated false，说明它没有被创建
        if (isViewCreated) {
            // 对于情况2,需要分情况考虑,如果是不可见 -> 可见 2.1
            // 如果是可见 -> 不可见 2.2
            // 对于2.1）我们需要如何判断呢？首先必须是可见的（isVisibleToUser
            // 为true）而且只有当可见状态进行改变的时候才需要切换，否则会出现反复调用的情况
            // 从而导致事件分发带来的多次更新
            if (isVisibleToUser && !currentVisibleState) {
                // 从不可见 -> 可见
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && currentVisibleState) {
                dispatchUserVisibleHint(false);
            }
        }
    }


    /**
     * 用FragmentTransaction来控制fragment的hide和show时，
     * 那么这个方法就会被调用。每当你对某个Fragment使用hide 或者是show的时候，那么这个Fragment就会自动调用这个方法。
     */

    @Override
    public void onHiddenChanged(boolean hidden) {
        System.out.println("onHiddenChanged: " + hidden);
        super.onHiddenChanged(hidden);
        // 这里的可见返回为false
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    /**
     * 统一处理用户可见事件分发
     */
    private void dispatchUserVisibleHint(boolean isVisible) {
        System.out.println("dispatchUserVisibleHint: " + isVisible);
        // 首先考虑一下fragment嵌套fragment的情况(只考虑2层嵌套)
        if (isVisible && isParentInvisible()) {
            // 父Fragmnet此时不可见,直接return不做处理
            return;
        }
        // 为了代码严谨,如果当前状态与需要设置的状态本来就一致了,就不处理了
        if (currentVisibleState == isVisible) {
            return;
        }
        currentVisibleState = isVisible;
        if (isVisible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                // 第一次可见,进行全局初始化
                onFragmentFirstVisible();
            }
            onFragmentResume();
            // 分发事件给内嵌的Fragment
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibleState(false);
        }

    }

    /**
     * 在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
     * 此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
     * 需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
     */
    private void dispatchChildVisibleState(boolean visible) {
        System.out.println("dispatchChildVisibleState " + visible);
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (null != fragments) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof MvvmLazyFragment && !fragment.isHidden()
                        && fragment.getUserVisibleHint()) {
                    ((MvvmLazyFragment) fragment).dispatchUserVisibleHint(visible);
                }
            }
        }

    }

    /**
     * Fragment真正的Pause,暂停一切网络耗时操作
     */
    protected void onFragmentPause() {
        System.out.println("onFragmentPause \" + \" 真正的Pause 结束相关操作耗时");
    }

    /**
     * Fragment真正的Resume,开始处理网络加载等耗时操作
     */
    protected void onFragmentResume() {
        System.out.println("onFragmentResume\" + \" 真正的Resume 开始相关操作耗时");
    }

    private boolean isParentInvisible() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof MvvmLazyFragment) {
            MvvmLazyFragment fragment = (MvvmLazyFragment) parentFragment;
            return !fragment.isSupportVisible();
        }
        return false;
    }

    private boolean isSupportVisible() {
        return currentVisibleState;
    }

    /**
     * 在滑动或者跳转的过程中，第一次创建fragment的时候均会调用onResume方法
     */
    @Override
    public void onResume() {
        super.onResume();
        // 如果不是第一次可见
        if (!mIsFirstVisible) {
            // 如果此时进行Activity跳转,会将所有的缓存的fragment进行onResume生命周期的重复
            // 只需要对可见的fragment进行加载,
            if (!isHidden() && !currentVisibleState && getUserVisibleHint()) {

                dispatchUserVisibleHint(true);
            }
        }

    }

    /**
     * 只有当当前页面由可见状态转变到不可见状态时才需要调用 dispatchUserVisibleHint currentVisibleState &&
     * getUserVisibleHint() 能够限定是当前可见的 Fragment 当前 Fragment 包含子 Fragment 的时候
     * dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见 子 fragment 走到这里的时候自身又会调用一遍
     */
    @Override
    public void onPause() {
        super.onPause();
        if (currentVisibleState && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 第一次可见,根据业务进行初始化操作
     */
    protected void onFragmentFirstVisible() {
        System.out.println("onFragmentFirstVisible  第一次可见");
        getData();
    }
}


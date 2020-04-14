package com.example.mvp_1.mvp.ui.common;

import android.os.Bundle;
import android.view.View;

import com.example.mvp_1.base.BaseFragment;
import com.example.mvp_1.base.BasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//拥有懒加载的Fragment
public abstract class LazyFragment extends BaseFragment {
    //默认Fragment没有被初始化
    private boolean mInitView = false;
    // 默认Fragment没有加载数据
    private boolean mLoadMore = false;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInitView = true;
        // 延迟加载的方法
        initLazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initLazyLoad();
    }

    protected void initLazyLoad(){
        // 如果Fragment的view没有被初始化  就把方法返回出去
        if (!mInitView)
            return;
        // Fragment对用户可见
        if (getUserVisibleHint()){
            lazyLoad();
            //代表加载完数据了
            mLoadMore = true;
            //Fragment代表对用户不可见
        }else {
            //如果有数据了，才停止加载
            if (mLoadMore){
                stopLoad();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInitView = false;
        mLoadMore = false;
    }

    protected abstract void stopLoad();

    protected abstract void lazyLoad();
    @Override
    protected abstract void initInject();

    @Override
    protected abstract BasePresenter createPresenter();

    @Override
    protected abstract int getLayoutId();
    @Override
    public abstract void stateScuess(Object o);

    @Override
    public abstract void stateError(String str);
}

package com.example.mvp_1.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  V层的对象持有P层的引用  拿到P层对象然后向P层发送请求数据的指令(让具体的实现类去请求对应的数据）
 *  拿到P层对象关联V层的生命周期  拿到P层对象释放V层引用
 * @param <P>
 * @param <T>
 */
public abstract class BaseFragment<P extends BasePresenter,T>
        extends Fragment implements IBaseView<T> {

    private Unbinder mUnbinder;
    private P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View view = null;
        if (layoutId != 0){
            view=inflater.inflate(layoutId,container,false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        if (mPresenter == null)
            mPresenter = createPresenter();
        // P层关联V层的生命周期
        mPresenter.attachView(this);
        //注入的方法
        initInject();

    }

    protected abstract void initInject();

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    public P getmPresenter(){
        if (mPresenter != null){
            return mPresenter;
        }
        return null;
    }

}
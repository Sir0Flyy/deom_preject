package com.example.mvp_1.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * P 层的基类  关联V层的生命周期  网络开关容器储存所有的网络开关
 * @param <V>
 * @param <T>
 */
public abstract class BasePresenter<V extends IBaseView<T>,T> implements IBasePresenter<T> {

    private WeakReference<V> mWr;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void start() {

    }

    @Override
    public void start(T t) {

    }
    //使用弱引用对象来修饰
    public void attachView(V view){
        if (mWr == null)
            mWr=new WeakReference<V>(view);
    }
    public void detachVIew(){
        if (mWr != null){
            mWr.clear();
            mWr = null;
        }
        deleteDisposable();
    }

    public void addDisposable(Disposable disposable){
        if (mCompositeDisposable ==null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }
    public void deleteDisposable(){
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()){
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable =null;
        }
    }
}

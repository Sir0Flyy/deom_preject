package com.example.mvp_1.callback;

import io.reactivex.disposables.Disposable;

public abstract class ProgressDataCallback<T> implements IDataCallBack<T> {
    abstract void onProgressChange(int progress);
    @Override
    public void onStateSucess(T t) {


    }

    @Override
    public void onStateError(String str) {

    }

    @Override
    public void onResponseDisposable(Disposable disposable) {

    }
}

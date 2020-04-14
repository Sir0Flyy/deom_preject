package com.example.mvp_1.callback;

import io.reactivex.disposables.Disposable;

public interface IDataCallBack<T> {
    void onStateSucess(T t);
    void onStateError(String str);
    void onResponseDisposable(Disposable disposable);
}

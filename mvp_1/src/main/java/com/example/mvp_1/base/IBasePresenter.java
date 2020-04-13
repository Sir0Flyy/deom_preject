package com.example.mvp_1.base;

/**
 * P 层的接口
 */
public interface IBasePresenter<T> {
    void start();
    void start(T t);
}

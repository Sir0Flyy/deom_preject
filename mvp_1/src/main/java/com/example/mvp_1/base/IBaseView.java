package com.example.mvp_1.base;

/**
 * View层接口
 */
public interface IBaseView<T> {
    //请求正确
    void stateScuess(T t);
    //失败的状态
    void stateError(String str);
}

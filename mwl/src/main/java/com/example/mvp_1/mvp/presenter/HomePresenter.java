package com.example.mvp_1.mvp.presenter;

import com.example.mvp_1.base.BasePresenter;
import com.example.mvp_1.callback.IDataCallBack;
import com.example.mvp_1.mvp.model.RxOperateImpl;
import com.example.mvp_1.mvp.model.api.Constants;
import com.example.mvp_1.mvp.ui.fragment.HomeFragment;
import com.example.mvp_1.util.LogUtils;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class HomePresenter<T> extends BasePresenter {
    private HomeFragment mHomeFragment;
    private RxOperateImpl mImpl;

    public HomePresenter(HomeFragment homeFragment) {
        this.mHomeFragment = homeFragment;
        mImpl = new RxOperateImpl();
    }

    @Override
    public void start(Object o) {
        super.start(o);
        if (o instanceof Integer){
            Integer type = (Integer)o;
            switch (type){
                case 0:
                    mImpl.requestData(Constants.GONGZHONGHAP_URL, new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody) {
                                ResponseBody body = (ResponseBody) t;
                                String jsonStr = null;
                                try {
                                    jsonStr = body.string();
                                    LogUtils.e(jsonStr);
                                    //TODO GSON解析 解析完之后 将解析的结果交给V层更新UI
                                    //交给V层更新UI
                                    mHomeFragment.stateScuess(t);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //交给V层更新UI
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            //交给V层更新UI
                            mHomeFragment.stateError(msg);
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            //添加获取的网络开关
                            addDisposable(disposable);
                        }
                    });
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                   mImpl.requestData(Constants.TAB_URL, new IDataCallBack<T>() {
                       @Override
                       public void onStateSucess(T t) {
                           if (t instanceof ResponseBody){
                               ResponseBody body = (ResponseBody) t;
                               try {
                                   String strJson = body.string();
                                   LogUtils.e(strJson);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }

                           }
                       }

                       @Override
                       public void onStateError(String str) {
                           mHomeFragment.stateScuess(str);
                       }

                       @Override
                       public void onResponseDisposable(Disposable disposable) {
                           addDisposable(disposable);
                       }
                   });
                    break;
            }
        }
    }
}

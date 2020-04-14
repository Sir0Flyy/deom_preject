package com.example.mvp_1.mvp.ui.fragment;

import com.example.mvp_1.R;
import com.example.mvp_1.base.BasePresenter;
import com.example.mvp_1.mvp.presenter.HomePresenter;
import com.example.mvp_1.mvp.ui.common.LazyFragment;
import com.example.mvp_1.util.LogUtils;

public class HomeFragment extends LazyFragment {
    private int mType;
    public HomeFragment(int type) {
        this.mType = type;
    }

    @Override
    protected void stopLoad() {
        switch (mType){
            case 0:
                LogUtils.e("home_Fragment停止加载");
                break;
            case 1:
                LogUtils.e("navigation_Fragment停止加载");
                break;
            case 2:
                LogUtils.e("tixi_Fragment停止加载");
                break;
            case 3:
                LogUtils.e("gongzhonghao_Fragment停止加载");
                break;
        }
    }

    @Override
    protected void lazyLoad() {
        getmPresenter().start(mType);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        Integer layoutId = switchId(mType);
        if (layoutId != null){
            return layoutId;
        }
        return 0;
    }

    private static Integer switchId(int mType) {
        switch (mType){
            case 0:
                return R.layout.home_fragment;
            case 1:
                return R.layout.navigation_fragment;
            case 2:
                return R.layout.tixi_fragment;
            case 3:
                return R.layout.gongzhonghao_fragment;
        }
        return null;
    }

    @Override
    public void stateScuess(Object o) {

    }

    @Override
    public void stateError(String str) {

    }
}

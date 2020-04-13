package com.example.mvp_1.mvp.presenter;

import com.example.mvp_1.base.BasePresenter;

public class HomePresenter extends BasePresenter {
    @Override
    public void start(Object o) {
        super.start(o);
        if (o instanceof Integer){
            Integer type = (Integer)o;
            switch (type){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    }
}

package com.example.mvp_1.base;

import android.os.Bundle;

import com.example.mvp_1.app.App;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = getLayout();
        if (layout != 0)
            setContentView(layout);
        App.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        //创建视图的方法
        onViewCreated();
        initListenner();
    }

    protected abstract void initListenner();

    protected abstract void onViewCreated();

    protected abstract int getLayout();

    @Override
    public void stateScuess(T t) {

    }
    @Override
    public void stateError(String str) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
            unbinder = null;
        }
        App.getInstance().romeActivity();
    }
}

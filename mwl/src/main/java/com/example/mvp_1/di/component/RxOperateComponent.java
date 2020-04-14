package com.example.mvp_1.di.component;


import com.example.mvp_1.di.annotation.PerSinglelton;
import com.example.mvp_1.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface RxOperateComponent {
    void inject(RxOperateImpl rxOperate);
}

package com.example.mvp_1.mvp.ui.activity;

import android.os.Bundle;
import com.example.mvp_1.R;
import com.example.mvp_1.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.home_vp)
    ViewPager mHomeVp;
    @BindView(R.id.home_navigation)
    BottomNavigationView mHomeNavigation;

    @Override
    protected void initListenner() {
        mHomeNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_home:
                    switchTab(0);
                    break;
                case R.id.item_navigation:
                    switchTab(1);
                    break;
                case R.id.item_gongzhonghao:
                    switchTab(2);
                    break;
                case R.id.item_tixi:
                    switchTab(3);
                    break;
            }
            return true;
        });
    }

    private void switchTab(int i) {
        mHomeVp.setCurrentItem(i);
    }

    @Override
    protected void onViewCreated() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:OnCreate Method has been created, run ButterKnife again to generate code
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}

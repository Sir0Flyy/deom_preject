package com.example.mvp_1.mvp.ui.activity;

import com.example.mvp_1.R;
import com.example.mvp_1.base.BaseActivity;
import com.example.mvp_1.mvp.ui.adapter.HomeFpAdapter;
import com.example.mvp_1.mvp.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

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
                case R.id.item_tixi:
                    switchTab(2);
                    break;
                case R.id.item_gongzhonghao:
                    switchTab(3);
                    break;
            }
            return true;
        });
        mHomeVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当ViewPager页面切换的时候 下面的tab标签跟着切换
                mHomeNavigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void switchTab(int i) {
        mHomeVp.setCurrentItem(i);
    }

    @Override
    protected void onViewCreated() {
        List<HomeFragment> homeFragments = getHomeFragments();
        HomeFpAdapter homeFpAdapter = new HomeFpAdapter(getSupportFragmentManager(), homeFragments);
        mHomeVp.setAdapter(homeFpAdapter);
    }

    private List<HomeFragment> getHomeFragments() {
        List<HomeFragment> fragments=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeFragment homeFragment = new HomeFragment(i);
            fragments.add(homeFragment);
        }
        return fragments;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}

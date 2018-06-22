package com.pakpobox.cleanpro.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pakpobox.cleanpro.R;
import com.pakpobox.cleanpro.base.BaseFragmentActivity;
import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.my.MyFragment;
import com.pakpobox.cleanpro.ui.order.OrdersFragment;
import com.pakpobox.cleanpro.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.main_container)
    FrameLayout mContainer;
    @BindView(R.id.main_bnv)
    BottomNavigationView mBottomNavigation;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private int mCurrentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        StatusBarUtil.transparencyBar(this);
//        StatusBarUtil.StatusBarLightMode(this);

        SupportFragment homeFragment = findFragment(HomeFragment.class);
        if (homeFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = OrdersFragment.newInstance();
            mFragments[2] = MyFragment.newInstance();

            loadMultipleRootFragment(R.id.main_container, 0, mFragments);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = homeFragment;
            mFragments[1] = findFragment(OrdersFragment.class);
            mFragments[2] = findFragment(MyFragment.class);
        }

        initView();
    }

    private void initView() {
        mBottomNavigation.setItemIconTintList(null);
        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int currentItem;
                switch (item.getItemId()) {
                    case R.id.menu_main_bottom_nav_home:
                        currentItem = 0;
                        break;
                    case R.id.menu_main_bottom_nav_orders:
                        currentItem = 1;
                        break;
                    case R.id.menu_main_bottom_nav_my:
                        currentItem = 2;
                        break;
                    default:
                        currentItem = 0;
                }
                showHideFragment(mFragments[currentItem], mFragments[mCurrentItem]);
                mCurrentItem = currentItem;
                return true;
            }
        });
    }
}

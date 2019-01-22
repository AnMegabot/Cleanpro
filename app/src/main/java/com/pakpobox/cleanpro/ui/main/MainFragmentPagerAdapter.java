package com.pakpobox.cleanpro.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.pakpobox.cleanpro.ui.home.HomeFragment;
import com.pakpobox.cleanpro.ui.location.LocationFragment;
import com.pakpobox.cleanpro.ui.my.MyFragment;
import com.pakpobox.cleanpro.ui.order.OrdersFragment;

import java.lang.ref.WeakReference;

/**
 * User:Sean.Wei
 * Date:2018/3/6
 * Time:17:24
 * 弃用（首页弃用ViewPager）
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private static int ITEM_COUNT = 4;
    private WeakReference<Fragment> mFragmentWeakReference;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance();
            case 1:
                return LocationFragment.newInstance();
            case 2:
                return OrdersFragment.newInstance();
            case 3:
                return MyFragment.newInstance();
            default:
                return null;
        }
    }

    @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mFragmentWeakReference = new WeakReference<>((Fragment) object);
    }

    @Override public int getCount() {
        return ITEM_COUNT;
    }

    public Fragment getCurrentFragment() {
        return mFragmentWeakReference.get();
    }
}

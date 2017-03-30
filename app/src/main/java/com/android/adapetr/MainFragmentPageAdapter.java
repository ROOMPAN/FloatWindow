package com.android.adapetr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyanjun on 15/10/23.
 * 首页Tab页ViewPager Adapter
 */
public class MainFragmentPageAdapter extends FragmentPagerAdapter {

    public MainFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList = fragmentList;
    }
    public MainFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, List<String> pagertitle) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.pagertitle=pagertitle;
    }

    private ArrayList<Fragment> mFragmentList;
    private List<String> pagertitle;



    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagertitle.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}

package com.rifcode.masvideochat.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainVPAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList = new ArrayList<>();

    public MainVPAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }
    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    public void addFragment(Fragment fragment) {
        mList.add(fragment);

    }


}
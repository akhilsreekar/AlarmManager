package com.akhilsreekar.artoo.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.akhilsreekar.artoo.Fragments.AddMeetingsFragment;
import com.akhilsreekar.artoo.Fragments.ViewMeetingsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 22-12-2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AddMeetingsFragment();
            case 1:
                return new ViewMeetingsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}

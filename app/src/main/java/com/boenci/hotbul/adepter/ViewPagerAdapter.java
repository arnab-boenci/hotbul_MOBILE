package com.boenci.hotbul.adepter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boenci.hotbul.model.AllSeason;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    private List<String> tabTitles;
    private List<ArrayList<String>> dataSetList;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> tabTitles, List<ArrayList<String>> dataSetList) {
        super(fragmentActivity);
        this.context = fragmentActivity;
        this.tabTitles = tabTitles;
        this.dataSetList = dataSetList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TabFragment.newInstance(dataSetList.get(position));
    }

    @Override
    public int getItemCount() {
        return tabTitles.size();
    }
}

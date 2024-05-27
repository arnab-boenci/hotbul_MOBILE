package com.boenci.hotbul.adepter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.boenci.hotbul.model.AllSeason;

import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    List<AllSeason.Datum> listDta;
    public TabAdapter(FragmentManager fm, int NumOfTabs, List<AllSeason.Datum> listDta) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.listDta = listDta;
    }

    @Override
    public Fragment getItem(int position) {

        return new DynamicFragment(position,listDta.get(position).episodeContentList);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

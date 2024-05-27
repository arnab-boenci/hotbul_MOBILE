package com.boenci.hotbul.adepter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.boenci.hotbul.R;
import com.boenci.hotbul.model.AllSeason;

import java.util.List;


public class DynamicFragment extends Fragment {
    View view;
    int val;
    RecyclerView episodListRecyclerview;
    AdapterEpisodsList adapterEpisodsList;
    List<AllSeason.Datum.Episode> episodes;


    public DynamicFragment() {
    }

    public DynamicFragment(int val, List<AllSeason.Datum.Episode> episodes) {
        this.episodes = episodes;
        this.val = val;
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dynamic, container, false);
       // val = getArguments().getInt("someInt", 0);
        episodListRecyclerview = view.findViewById(R.id.episodListRecyclerview);


//        episodes = allSeasonsEp11.get(val).episodes;





        adapterEpisodsList = new AdapterEpisodsList(view.getContext(),episodes);
        episodListRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        episodListRecyclerview.setAdapter(adapterEpisodsList);
        adapterEpisodsList.notifyDataSetChanged();
        return view;
    }






    public static DynamicFragment addfrag(int val) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }
}

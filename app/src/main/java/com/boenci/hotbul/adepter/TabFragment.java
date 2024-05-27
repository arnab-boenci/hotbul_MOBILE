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

import java.util.ArrayList;
import java.util.List;


public class TabFragment extends Fragment {

    private ArrayList<String> dataSet = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    public static TabFragment newInstance(ArrayList<String> dataSet) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("dataSet", dataSet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataSet = getArguments().getStringArrayList("dataSet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.episodListRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(adapter);
    }
}

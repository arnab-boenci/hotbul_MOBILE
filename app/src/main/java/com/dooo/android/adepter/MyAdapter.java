package com.dooo.android.adepter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dooo.android.R;
import com.dooo.android.WebSeriesDetails;
import com.dooo.android.model.AllSeason;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

private ArrayList<String> dataSet;

public MyAdapter(ArrayList<String> dataSet) {
        this.dataSet = dataSet;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episods_item, parent, false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    //AllSeason.Datum.Episode tvitem = dataSet.get(position);
   // holder.nameAc.setText(tvitem.episodename);
}

@Override
public int getItemCount() {
        return dataSet.size();
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageViewActros;
    TextView nameAc;
    TextView detailsEpisod;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewActros = itemView.findViewById(R.id.imageViewActros);
        nameAc = itemView.findViewById(R.id.nameAc);
        detailsEpisod = itemView.findViewById(R.id.detailsEpisod);
        // cardview = itemView.findViewById(R.id.cardview);

    }
}
}
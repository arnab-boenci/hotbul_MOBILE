package com.boenci.hotbul.adepter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.boenci.hotbul.AppConfig;
import com.boenci.hotbul.MovieDetails;
import com.boenci.hotbul.R;
import com.boenci.hotbul.WebSeriesDetails;
import com.boenci.hotbul.list.MostSearchedList;

import java.util.List;

public class MostSearchedListAdepter extends RecyclerView.Adapter<MostSearchedListAdepter.MyViewHolder> {
    private Context mContext;
    private List<MostSearchedList> mData;

    public MostSearchedListAdepter(Context mContext, List<MostSearchedList> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MostSearchedListAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = view = LayoutInflater.from(parent.getContext()).inflate(AppConfig.contentItem, parent, false);
        return new MostSearchedListAdepter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostSearchedListAdepter.MyViewHolder holder, int position) {
        holder.setTitle(mData.get(position));
        holder.setYear(mData.get(position));
        holder.setImage(mData.get(position));

        holder.IsPremium(mData.get(position));

        holder.Movie_Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mData.get(position).getContentType()==1) {
                    Intent intent = new Intent(mContext, MovieDetails.class);
                    intent.putExtra("ID", mData.get(position).getID());
                    mContext.startActivity(intent);
                } else if(mData.get(position).getContentType()==2) {
                    Intent intent = new Intent(mContext, WebSeriesDetails.class);
                    intent.putExtra("ID", mData.get(position).getID());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Title;
        TextView Year;
        ImageView Thumbnail;

        View Premium_Tag;

        CardView Movie_Item;

        CardView Show_All;
        TextView showAllText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.Movie_list_Title);
            Year = (TextView) itemView.findViewById(R.id.Movie_list_Year);
            Thumbnail = (ImageView) itemView.findViewById(R.id.Movie_Item_thumbnail);

            Premium_Tag = (View) itemView.findViewById(R.id.Premium_Tag);

            Movie_Item = itemView.findViewById(R.id.Movie_Item);

            Show_All = itemView.findViewById(R.id.show_all);
            showAllText= (TextView)  itemView.findViewById(R.id.showAllText);
        }

        void IsPremium(MostSearchedList type) {
            if(AppConfig.all_movies_type == 0) {
                if(type.getType() == 1) {
                    Premium_Tag.setVisibility(View.VISIBLE);
                } else {
                    Premium_Tag.setVisibility(View.GONE);
                }
            } else if(AppConfig.all_movies_type == 1) {
                Premium_Tag.setVisibility(View.GONE);
            } else if(AppConfig.all_movies_type == 2) {
                Premium_Tag.setVisibility(View.VISIBLE);
            }
        }

        void setTitle(MostSearchedList title_text) {
            Title.setText(title_text.getTitle());
        }

        void setYear(MostSearchedList year_text) {
            Year.setText(year_text.getYear());
        }

        void setImage(MostSearchedList Thumbnail_Image) {
            if(AppConfig.safeMode) {
                Glide.with(mContext)
                        .load(R.drawable.thumbnail_placeholder)
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(Thumbnail);
            } else {
                Glide.with(mContext)
                        .load(Thumbnail_Image.getThumbnail())
                        .placeholder(R.drawable.thumbnail_placeholder)
                        .into(Thumbnail);
            }
        }
    }
}

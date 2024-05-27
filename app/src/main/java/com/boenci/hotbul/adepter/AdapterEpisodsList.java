package com.boenci.hotbul.adepter;

import static android.content.Context.MODE_PRIVATE;

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
import com.boenci.hotbul.R;
import com.boenci.hotbul.WebSeriesDetails;
import com.boenci.hotbul.model.AllSeason;

import java.util.List;



public class AdapterEpisodsList extends RecyclerView.Adapter<AdapterEpisodsList.StreamItView> {

    List<AllSeason.Datum.Episode> episodes;
    Context context;


    SharedPreferences sharedPreferences;
    boolean isDark;

    public AdapterEpisodsList(Context context , List<AllSeason.Datum.Episode> episodes) {
        this.episodes = episodes;
        this.context = context;
        //sharedPreferences = context.getSharedPreferences("mypref", MODE_PRIVATE);
        //notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StreamItView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episods_item, parent, false);
        // return itemView
        return new StreamItView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StreamItView holder, int position) {

      //  sharedPreferences = context.getSharedPreferences("MYPREFERENCE", MODE_PRIVATE);
      //  isDark = sharedPreferences.getBoolean("dark", true);
        AllSeason.Datum.Episode tvitem = episodes.get(position);
        holder.nameAc.setText(tvitem.episodename);
        try {
            String plainText = Html.fromHtml(tvitem.description).toString();
            holder.detailsEpisod.setText(plainText);
        }catch (Exception e){
            holder.detailsEpisod.setText("");
        }

        Glide.with(holder.itemView)
                .load(tvitem.posterimageurl)
                .placeholder(R.drawable.thumbnail_placeholder)
                .thumbnail(.3f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewActros);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebSeriesDetails.class);
                intent.putExtra("ID", tvitem.videocontentid + "");
//                intent.putExtra("name", tvitem.name + "");
//                intent.putExtra("image", tvitem.image + "");
//                intent.putExtra("cat_id", tvitem.catId + "");
//                intent.putExtra("url", tvitem.url + "");
//                intent.putExtra("url_type", tvitem.urlType + "");
//                intent.putExtra("cntry_id", tvitem.cntryId + "");
//                intent.putExtra("token", tvitem.token + "");
//                intent.putExtra("token_type", tvitem.tokenType + "");
//                intent.putExtra("user_agent", tvitem.userAgent + "");
//                intent.putExtra("agent_type", tvitem.agentType + "");
//                intent.putExtra("watch_ads", tvitem.watchAds + "");
//                intent.putExtra("description", tvitem.description + "");
//                intent.putExtra("created_at", tvitem.createdAt + "");
//                intent.putExtra("updated_at", tvitem.updatedAt + "");
//                String tvCatName;
//
//                intent.putExtra("tv_cat_name",  "video");
//                intent.putExtra("type_name", tvitem.typeName + "");
//                intent.putExtra("country_name",  "sdfsdfsd");
//                intent.putExtra("token_name",   "");
//                intent.putExtra("view_count", tvitem.viewCount + "");
//                intent.putExtra("content_type", tvitem.contentType + "");
//                intent.putExtra("subscription", tvitem.subscription + "");
//                intent.putExtra("banner_image", tvitem.bannerImage + "");
//                intent.putExtra("prdct_price", tvitem.prdctPrice + "");
//                intent.putExtra("prdct_key", tvitem.prdctKey + "");
//                intent.putExtra("urlOrVideo", "video");
//                intent.putExtra("ratings", tvitem.ratings + "");
//                intent.putExtra("noVChangSerisData","no");
                v.getContext().startActivity(intent);
               // new AnimIntent.Builder(v.getContext()).performSlideToLeft();

            }
        });


    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }


    public class StreamItView extends RecyclerView.ViewHolder {
       // CardView cardview;
        ImageView imageViewActros;
        TextView nameAc;
        TextView detailsEpisod;

        public StreamItView(@NonNull View itemView) {
            super(itemView);
            imageViewActros = itemView.findViewById(R.id.imageViewActros);
            nameAc = itemView.findViewById(R.id.nameAc);
            detailsEpisod = itemView.findViewById(R.id.detailsEpisod);
           // cardview = itemView.findViewById(R.id.cardview);

        }
    }
}

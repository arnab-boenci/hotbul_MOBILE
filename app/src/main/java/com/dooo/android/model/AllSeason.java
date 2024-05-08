package com.dooo.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSeason {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("series")
    @Expose
    public Series series;
    @SerializedName("resultList")
    @Expose
    public List<Datum> resultList = null;



    public class Datum {

        @SerializedName("seriesseasonid")
        @Expose
        public Integer seriesseasonid;
        @SerializedName("seasonname")
        @Expose
        public String seasonname;
//        @SerializedName("series_id")
//        @Expose
//        public Integer seriesId;
        @SerializedName("seasonorder")
        @Expose
        public Integer seasonorder;
        @SerializedName("caller")
        @Expose
        public String caller;
        @SerializedName("videocontentid")
        @Expose
        public Integer videocontentid;
        @SerializedName("createdat")
        @Expose
        public String createdat;
        @SerializedName("updatedat")
        @Expose
        public String updatedat;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("deletedflag")
        @Expose
        public String deletedflag;
        @SerializedName("action")
        @Expose
        public String action;
        @SerializedName("updatedby")
        @Expose
        public String updatedby;
        @SerializedName("createdby")
        @Expose
        public String createdby;
        @SerializedName("episodeContentList")
        @Expose
        public List<Episode> episodeContentList = null;



        public class Episode {

            @SerializedName("episodecontentid")
            @Expose
            public Integer episodecontentid;
            @SerializedName("episodename")
            @Expose
            public String episodename;
            @SerializedName("image")
            @Expose
            public String image;
            @SerializedName("posterimageurl")
            @Expose
            public String posterimageurl;
            @SerializedName("thumbnailimageurl")
            @Expose
            public String thumbnailimageurl;

            @SerializedName("url")
            @Expose
            public String url;
            @SerializedName("episodenumber")
            @Expose
            public Integer episodenumber;

            @SerializedName("releasedate")
            @Expose
            public String releasedate;
            @SerializedName("duration")
            @Expose
            public String duration;
            @SerializedName("ispremium")
            @Expose
            public String ispremium;

            @SerializedName("subscription")
            @Expose
            public String subscription;
            @SerializedName("videocontentid")
            @Expose
            public Integer videocontentid;
            @SerializedName("ratings")
            @Expose
            public Double ratings;
            @SerializedName("seriesseasonid")
            @Expose
            public Integer seriesseasonid;

            @SerializedName("description")
            @Expose
            public String description;

        }

        public class Content {

            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("prdct_name")
            @Expose
            public String prdctName;
            @SerializedName("prdct_price")
            @Expose
            public String prdctPrice;
            @SerializedName("prdct_key")
            @Expose
            public String prdctKey;
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public String updatedAt;

        }



    }

    public class Series {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("series_name")
        @Expose
        public String seriesName;
        @SerializedName("thumbnail_image")
        @Expose
        public String thumbnailImage;
        @SerializedName("poster_image")
        @Expose
        public String posterImage;
        @SerializedName("trailer")
        @Expose
        public String trailer;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("release_date")
        @Expose
        public String releaseDate;
        @SerializedName("thumbUrl")
        @Expose
        public String thumbUrl;
        @SerializedName("posterUrl")
        @Expose
        public String posterUrl;
        @SerializedName("imgType")
        @Expose
        public String imgType;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("actors")
        @Expose
        public List<Actor> actors = null;



        public class Actor {

            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("star_type")
            @Expose
            public String starType;
            @SerializedName("star_name")
            @Expose
            public String starName;
            @SerializedName("photo")
            @Expose
            public String photo;
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public String updatedAt;
            @SerializedName("pivot")
            @Expose
            public Pivot pivot;

            public class Pivot {

                @SerializedName("series_id")
                @Expose
                public Integer seriesId;
                @SerializedName("star_id")
                @Expose
                public Integer starId;

            }
        }



    }








}

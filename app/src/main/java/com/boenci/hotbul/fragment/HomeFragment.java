package com.boenci.hotbul.fragment;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;
import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.bumptech.glide.Glide;
import com.boenci.hotbul.AllMoviesActivity;
import com.boenci.hotbul.AllWebSeriesActivity;
import com.boenci.hotbul.AppConfig;
import com.boenci.hotbul.Home;
import com.boenci.hotbul.LiveTv;
import com.boenci.hotbul.MoviesActivity;
import com.boenci.hotbul.MusicActivity;
import com.boenci.hotbul.R;
import com.boenci.hotbul.ShortFilmActivity;
import com.boenci.hotbul.Splash;
import com.boenci.hotbul.WebSeriesActivity;
import com.boenci.hotbul.WebView;
import com.boenci.hotbul.adepter.ContinuePlayingListAdepter;
import com.boenci.hotbul.adepter.GenreListAdepter;
import com.boenci.hotbul.adepter.HouseOfHorrorListAdepter;
import com.boenci.hotbul.adepter.ImageSliderAdepter;
import com.boenci.hotbul.adepter.LiveTvChannelListAdepter;
import com.boenci.hotbul.adepter.LiveTvGenreListAdepter;
import com.boenci.hotbul.adepter.MostSearchedListAdepter;
import com.boenci.hotbul.adepter.MovieListAdepter;
import com.boenci.hotbul.adepter.TrendingListAdepter;
import com.boenci.hotbul.adepter.WebSeriesListAdepter;
import com.boenci.hotbul.adepter.moviesOnlyForYouListAdepter;
import com.boenci.hotbul.adepter.webSeriesOnlyForYouListAdepter;
import com.boenci.hotbul.db.resume_content.ResumeContent;
import com.boenci.hotbul.db.resume_content.ResumeContentDatabase;
import com.boenci.hotbul.list.ContinuePlayingList;
import com.boenci.hotbul.list.GenreList;
import com.boenci.hotbul.list.HouseofHorrorList;
import com.boenci.hotbul.list.ImageSliderItem;
import com.boenci.hotbul.list.LiveTvChannelList;
import com.boenci.hotbul.list.LiveTvGenreList;
import com.boenci.hotbul.list.MostSearchedList;
import com.boenci.hotbul.list.MovieList;
import com.boenci.hotbul.list.TrendingList;
import com.boenci.hotbul.list.WebSeriesList;
import com.boenci.hotbul.sharedpreferencesmanager.ConfigManager;
import com.boenci.hotbul.sharedpreferencesmanager.UserManager;
import com.boenci.hotbul.utils.Constants;
import com.boenci.hotbul.utils.HelperUtils;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.RepeatModeUtil;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.Mrec;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.ads.video.VideoPlayerView;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class HomeFragment extends Fragment {

    Context context;
    int userID;
    LinearLayout genreLayout, LivetvgenreLayout, admobNativeadTemplateLayout, homeWebSeriesLayout,oldisgoldLayout,
            musicLayout,adultLayout;
    RecyclerView genre_list_Recycler_View, live_tv_genre_list_Recycler_View, movieListRecycleview, webSeriesListRecycleview,
            continuePlaying_list_Recycler_View, old_is_gold_list_Recycler_View, music_list_Recycler_View, adult_list_Recycler_View;
    LinearLayout recentMoviesLayout;
    RecyclerView home_Recent_Movies_list_Recycler_View;
    LinearLayout recentWebSeriesLayout;
    RecyclerView home_Recent_Series_list_Recycler_View;
    RecyclerView homeLiveTVlistRecyclerView;
    LinearLayout bywMovieLayoutLinearLayout;
    RecyclerView home_bywm_list_Recycler_View;
    LinearLayout bywWebSeriesLayout;
    RecyclerView home_bywws_list_Recycler_View;
    LinearLayout popularMoviesLayout;
    LinearLayout trendingLayout;
    RecyclerView home_popularMovies_list_Recycler_View;
    RecyclerView home_trending_list_Recycler_View;
    LinearLayout popularWebSeriesLayout;
    LinearLayout houseOfHorrorLayout;
    RecyclerView home_popularWebSeries_list_Recycler_View;
    RecyclerView house_of_horror_list_Recycler_View;
    SwipeRefreshLayout homeSwipeRefreshLayout;
    LinearLayout liveTvLayout;
    private ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();

    String imageSliderType;
    List<ImageSliderItem> imageSliderItems;

    int movieImageSliderMaxVisible;
    int webseriesImageSliderMaxVisible;
    int adType;
    int shuffleContents;
    int showMessage;
    String message_animation_url;
    String messageTitle;
    String message;
    private HelperUtils helperUtils;
    int liveTvVisiableInHome, live_tv_genre_visible_in_home, genre_visible_in_home;
    LinearLayout homeMovieLayout;

    boolean removeAds = false;
    boolean playPremium = false;
    boolean downloadPremium = false;
    public static LinearLayout resume_Layout;
    ImageView clearContinuePlaying;

    ImageView moreMovies, moreWebSeries, moreLiveTV, moreRecentMovies, moreRecentSeries;

    RelativeLayout bannerViewLayout;
    RelativeLayout adViewLayout;
    RelativeLayout ad_container;
    ImageView custom_footer_banner_ad;
    ImageView custom_banner_ad;
    TemplateView template;
    LinearLayout homeProfile;
    LinearLayout homeTopSearchedLayout;
    RecyclerView home_top_searched_list_Recycler_View;
    PlayerView custom_banner_video_ad, custom_footer_banner_video_ad;
    CardView bottom_floting_menu;
    TextView bottom_floting_menu_movies, bottom_floting_menu_web_series, bottom_floting_menu_music;
    TextView appName;
    ImageView appLogo;
    NestedScrollView nestedScrollView;

    ShimmerFrameLayout shimmer_view_container;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        loadhomecontentlist();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getActivity().getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View layoutInflater = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(layoutInflater);


        nestedScrollView = layoutInflater.findViewById(R.id.nestedScrollView);
        LinearLayout appBar = layoutInflater.findViewById(R.id.appBar);
        appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(0)+"090911"));
        bottom_floting_menu.animate()
                .translationY(bottom_floting_menu.getHeight())
                .alpha(0.0f)
                .setDuration(100);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                //Log.i(TAG, "Scroll DOWN");
                //appBar.getBackground().setAlpha(0);
                //Log.d("test", String.valueOf(scrollY));
                if(scrollY < 303) {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(scrollY)+"090911"));
                    //bottom_floting_menu.setVisibility(View.GONE);
                    bottom_floting_menu.animate()
                            .translationY(bottom_floting_menu.getHeight())
                            .alpha(0.0f)
                            .setDuration(100);
                } else {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
                    //bottom_floting_menu.setVisibility(View.VISIBLE);
                    bottom_floting_menu.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setDuration(100);
                }
            }
            if (scrollY < oldScrollY) {
                //Log.i(TAG, "Scroll UP");
                //Log.d("test", String.valueOf(scrollY));
                if(scrollY < 303) {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(scrollY)+"090911"));
                    //bottom_floting_menu.setVisibility(View.GONE);
                    bottom_floting_menu.animate()
                            .translationY(bottom_floting_menu.getHeight())
                            .alpha(0.0f)
                            .setDuration(100);
                } else {
                    appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
                    //bottom_floting_menu.setVisibility(View.VISIBLE);
                    bottom_floting_menu.animate()
                            .translationY(0)
                            .alpha(1.0f)
                            .setDuration(100);
                }
            }

            if (scrollY == 0) {
               // Log.i(TAG, "TOP SCROLL");
                appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(0)+"090911"));

            }

            if (scrollY == ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() )) {
                //Log.i(TAG, "BOTTOM SCROLL");
                appBar.setBackgroundColor(Color.parseColor("#"+HelperUtils.getAlphaColor(303)+"090911"));
            }
        });

        try {
            JSONObject userObject = UserManager.loadUser(context);
            userID = userObject.getInt("ID");
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        try {
            JSONObject configObject = ConfigManager.loadConfig(context);
            appName.setText(configObject.getString("name"));
            if(!configObject.getString("logo").equals("")) {
                if(configObject.getString("name").equals("")) {
                    appLogo.requestLayout();
                    appLogo.getLayoutParams().height=150;
                    appLogo.getLayoutParams().width=150;
                }
                Glide.with(context)
                        .load(configObject.getString("logo"))
                        .placeholder(R.drawable.boenci_logo_no_bg_red)
                        .into(appLogo);
            }

            imageSliderType = "0"; //configObject.getString("image_slider_type");
            movieImageSliderMaxVisible = configObject.getInt("movie_image_slider_max_visible");
            webseriesImageSliderMaxVisible = configObject.getInt("webseries_image_slider_max_visible");
            adType = configObject.getInt("ad_type");

            shuffleContents = configObject.getInt("shuffle_contents");

            showMessage  = configObject.getInt("Show_Message");
            message_animation_url  = configObject.getString("message_animation_url");
            messageTitle = configObject.getString("Message_Title");
            message = configObject.getString("Message");
            if(showMessage == 1) {
                if(!AppConfig.isCustomMessageShown) {
                    helperUtils = new HelperUtils(getActivity());
                    helperUtils.showMsgDialog(getActivity(), messageTitle, message, message_animation_url);
                    AppConfig.isCustomMessageShown = true;
                }
            }

            liveTvVisiableInHome = configObject.getInt("LiveTV_Visiable_in_Home");

            genre_visible_in_home = configObject.getInt("genre_visible_in_home");

            if(genre_visible_in_home == 0) {
                genreLayout.setVisibility(View.GONE);
            } else if(genre_visible_in_home == 1) {
                genreLayout.setVisibility(View.VISIBLE);
            } else {
                genreLayout.setVisibility(View.VISIBLE);
            }

            live_tv_genre_visible_in_home = configObject.getInt("live_tv_genre_visible_in_home");

            if(live_tv_genre_visible_in_home == 0) {
                LivetvgenreLayout.setVisibility(View.GONE);
            } else if(live_tv_genre_visible_in_home == 1) {
                LivetvgenreLayout.setVisibility(View.VISIBLE);
            } else {
                LivetvgenreLayout.setVisibility(View.VISIBLE);
            }

            if(configObject.getInt("home_bottom_floting_menu_status") == 1) {
                bottom_floting_menu.setVisibility(View.VISIBLE);
            } else {
                bottom_floting_menu.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        loadUserSubscriptionDetails();

        imageSliderItems = new ArrayList<>();
        imageSliderType = "0";

        viewPager2.setClipToPadding(true);
        viewPager2.setClipChildren(true);
        //viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1-Math.abs(position);
            page.setScaleY(0.85f + r * 0.20f);
            page.setScaleX(0.90f + r * 0.20f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        if(liveTvVisiableInHome == 0) {
            liveTvLayout.setVisibility(View.GONE);
        } else if(liveTvVisiableInHome == 1) {
            liveTvLayout.setVisibility(View.VISIBLE);
        } else  {
            liveTvLayout.setVisibility(View.VISIBLE);
        }
        nestedScrollView.setVisibility(View.GONE);
        shimmer_view_container.startShimmer();
        shimmer_view_container.setVisibility(View.VISIBLE);
        loadhomecontentlist();

        homeSwipeRefreshLayout.setOnRefreshListener(() -> {
            shimmer_view_container.startShimmer();
            nestedScrollView.setVisibility(View.GONE);
            shimmer_view_container.setVisibility(View.VISIBLE);
            loadhomecontentlist();
        });

        clearContinuePlaying.setOnClickListener(view -> {
            MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                    .setTitle("Clear Continue Playing List?")
                    .setMessage("You can't Revert this!")
                    .setCancelable(false)
                    .setAnimation(R.raw.delete)
                    .setPositiveButton("Yes", R.drawable.ic_baseline_exit, (dialogInterface, which) -> {
                        dialogInterface.dismiss();

                        ResumeContentDatabase db = ResumeContentDatabase.getDbInstance(context.getApplicationContext());
                        db.resumeContentDao().clearDB();
                        resume_Layout.setVisibility(View.GONE);
                    })
                    .setNegativeButton("NO", R.drawable.ic_baseline_exit, (dialogInterface, which) -> dialogInterface.dismiss())
                    .build();
            mDialog.show();
        });


        moreMovies.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllMoviesActivity.class);
            startActivity(intent);
        });

        moreWebSeries.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllWebSeriesActivity.class);
            startActivity(intent);
        });

        moreLiveTV.setOnClickListener(view -> {
            Intent intent = new Intent(context, LiveTv.class);
            startActivity(intent);
        });

        moreRecentMovies.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllMoviesActivity.class);
            startActivity(intent);
        });

        moreRecentSeries.setOnClickListener(view -> {
            Intent intent = new Intent(context, AllWebSeriesActivity.class);
            startActivity(intent);
        });

        //Ad Controller
        if(!removeAds) {
            loadAd();
        } else {
            admobNativeadTemplateLayout.setVisibility(View.GONE);
        }

        homeProfile.setOnClickListener(view ->{
            Home.bottomNavigationView.setSelectedItemId(R.id.account);
        });

        bottom_floting_menu_movies.setOnClickListener(view->{
            //startActivity(new Intent(getActivity(), MoviesActivity.class));
            MoviesFragment moviesFragment = new MoviesFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainFragment, moviesFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        bottom_floting_menu_web_series.setOnClickListener(view->{
            ShortFilmFragment shortFilmFragment = new ShortFilmFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainFragment, shortFilmFragment);
            transaction.addToBackStack(null);
            transaction.commit();
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mainFragment, new HomeFragment())
//                    .commit();
            //startActivity(new Intent(getActivity(), ShortFilmActivity.class));
        });
        bottom_floting_menu_music.setOnClickListener(view->{
            MusicFragment musicFragment = new MusicFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.mainFragment, musicFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            //startActivity(new Intent(getActivity(), MusicActivity.class));
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor), layoutInflater);

        return layoutInflater;
    }

    private void bindViews(View layoutInflater) {
        LivetvgenreLayout = layoutInflater.findViewById(R.id.LivetvgenreLayout);
        live_tv_genre_list_Recycler_View = layoutInflater.findViewById(R.id.live_tv_genre_list_Recycler_View);
        genreLayout = layoutInflater.findViewById(R.id.genreLayout);
        genre_list_Recycler_View = layoutInflater.findViewById(R.id.genre_list_Recycler_View);
        admobNativeadTemplateLayout = layoutInflater.findViewById(R.id.admob_nativead_template_layout);
        viewPager2 = layoutInflater.findViewById(R.id.ViewPagerImageSlider);
        homeMovieLayout = layoutInflater.findViewById(R.id.homeMovieLayout);
        movieListRecycleview = layoutInflater.findViewById(R.id.home_Movie_list_Recycler_View);
        homeSwipeRefreshLayout = layoutInflater.findViewById(R.id.Home_Swipe_Refresh_Layout);
        webSeriesListRecycleview = layoutInflater.findViewById(R.id.home_Web_Series_list_Recycler_View);
        homeWebSeriesLayout = layoutInflater.findViewById(R.id.homeWebSeriesLayout);
        recentMoviesLayout = layoutInflater.findViewById(R.id.recentMoviesLayout);
        home_Recent_Movies_list_Recycler_View = layoutInflater.findViewById(R.id.home_Recent_Movies_list_Recycler_View);
        recentWebSeriesLayout= layoutInflater.findViewById(R.id.recentWebSeriesLayout);
        home_Recent_Series_list_Recycler_View = layoutInflater.findViewById(R.id.home_Recent_Series_list_Recycler_View);
        homeLiveTVlistRecyclerView = layoutInflater.findViewById(R.id.home_Live_TV_list_Recycler_View);
        bywMovieLayoutLinearLayout= layoutInflater.findViewById(R.id.bywMovieLayout);
        home_bywm_list_Recycler_View = layoutInflater.findViewById(R.id.home_bywm_list_Recycler_View);
        bywWebSeriesLayout= layoutInflater.findViewById(R.id.bywWebSeriesLayout);
        home_bywws_list_Recycler_View = layoutInflater.findViewById(R.id.home_bywws_list_Recycler_View);
        popularMoviesLayout= layoutInflater.findViewById(R.id.popularMoviesLayout);
        trendingLayout= layoutInflater.findViewById(R.id.trendingLayout);
        home_popularMovies_list_Recycler_View = layoutInflater.findViewById(R.id.home_popularMovies_list_Recycler_View);
        home_trending_list_Recycler_View = layoutInflater.findViewById(R.id.home_trending_list_Recycler_View);
        music_list_Recycler_View = layoutInflater.findViewById(R.id.music_list_Recycler_View);
        popularWebSeriesLayout= layoutInflater.findViewById(R.id.popularWebSeriesLayout);
        houseOfHorrorLayout= layoutInflater.findViewById(R.id.houseOfHorrorLayout);
        musicLayout = layoutInflater.findViewById(R.id.musicLayout);
        oldisgoldLayout = layoutInflater.findViewById(R.id.oldisgoldLayout);
        adult_list_Recycler_View = layoutInflater.findViewById(R.id.adult_list_Recycler_View);
        adultLayout = layoutInflater.findViewById(R.id.adultLayout);
        home_popularWebSeries_list_Recycler_View = layoutInflater.findViewById(R.id.home_popularWebSeries_list_Recycler_View);
        house_of_horror_list_Recycler_View = layoutInflater.findViewById(R.id.house_of_horror_list_Recycler_View);
        old_is_gold_list_Recycler_View = layoutInflater.findViewById(R.id.old_is_gold_list_Recycler_View);
        liveTvLayout = layoutInflater.findViewById(R.id.LiveTV_Layout);
        continuePlaying_list_Recycler_View = layoutInflater.findViewById(R.id.continuePlaying_list_Recycler_View);
        resume_Layout= layoutInflater.findViewById(R.id.resume_Layout);
        clearContinuePlaying = layoutInflater.findViewById(R.id.clearContinuePlaying);
        moreMovies = layoutInflater.findViewById(R.id.More_Movies);
        moreWebSeries = layoutInflater.findViewById(R.id.More_WebSeries);
        moreLiveTV = layoutInflater.findViewById(R.id.More_Live_TV);
        moreRecentMovies = layoutInflater.findViewById(R.id.More_Recent_Movies);
        moreRecentSeries = layoutInflater.findViewById(R.id.More_Recent_Series);
        adViewLayout = layoutInflater.findViewById(R.id.ad_View_Layout);
        bannerViewLayout = layoutInflater.findViewById(R.id.banner_View_Layout);
        ad_container = layoutInflater.findViewById(R.id.ad_container);
        custom_footer_banner_ad = layoutInflater.findViewById(R.id.custom_footer_banner_ad);
        custom_banner_ad = layoutInflater.findViewById(R.id.custom_banner_ad);
        template = layoutInflater.findViewById(R.id.admob_native_template);
        homeProfile = layoutInflater.findViewById(R.id.homeProfile);
        homeTopSearchedLayout = layoutInflater.findViewById(R.id.homeTopSearchedLayout);
        home_top_searched_list_Recycler_View = layoutInflater.findViewById(R.id.home_top_searched_list_Recycler_View);
        custom_banner_video_ad = layoutInflater.findViewById(R.id.custom_banner_video_ad);
        custom_footer_banner_video_ad = layoutInflater.findViewById(R.id.custom_footer_banner_video_ad);
        bottom_floting_menu = layoutInflater.findViewById(R.id.bottom_floting_menu);
        bottom_floting_menu_movies = layoutInflater.findViewById(R.id.bottom_floting_menu_movies);
        bottom_floting_menu_web_series = layoutInflater.findViewById(R.id.bottom_floting_menu_web_series);
        bottom_floting_menu_music = layoutInflater.findViewById(R.id.bottom_floting_menu_music);
        shimmer_view_container = layoutInflater.findViewById(R.id.shimmer_view_container);
        appName = layoutInflater.findViewById(R.id.appName);
        appLogo = layoutInflater.findViewById(R.id.appLogo);
    }

    void setColorTheme(int color, View layoutInflater) {

        CardView LiveTVSmallCardPalate = layoutInflater.findViewById(R.id.LiveTVSmallCardPalate);
        LiveTVSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView continuePlayingSmallCardPalate = layoutInflater.findViewById(R.id.continuePlayingSmallCardPalate);
        continuePlayingSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView mostPopularMoviesSmallCardPalate = layoutInflater.findViewById(R.id.mostPopularMoviesSmallCardPalate);
        mostPopularMoviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView mostPopularWebseriesSmallCardPalate = layoutInflater.findViewById(R.id.mostPopularWebseriesSmallCardPalate);
        mostPopularWebseriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView houseOfHorrorSmallCardPalate = layoutInflater.findViewById(R.id.houseOfHorrorSmallCardPalate);
        houseOfHorrorSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView moviesOnlyForYouSmallCardPalate = layoutInflater.findViewById(R.id.moviesOnlyForYouSmallCardPalate);
        moviesOnlyForYouSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView webseriesOnlyForYouSmallCardPalate = layoutInflater.findViewById(R.id.webseriesOnlyForYouSmallCardPalate);
        webseriesOnlyForYouSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView recentlyAddedMoviesSmallCardPalate = layoutInflater.findViewById(R.id.recentlyAddedMoviesSmallCardPalate);
        recentlyAddedMoviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView recentlyAddedWebSeriesSmallCardPalate = layoutInflater.findViewById(R.id.recentlyAddedWebSeriesSmallCardPalate);
        recentlyAddedWebSeriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView moviesSmallCardPalate = layoutInflater.findViewById(R.id.moviesSmallCardPalate);
        moviesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView webSeriesSmallCardPalate = layoutInflater.findViewById(R.id.webSeriesSmallCardPalate);
        webSeriesSmallCardPalate.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    private void loadUserSubscriptionDetails() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String subscriptionType = sharedPreferences.getString("subscription_type", null);

        String number = String.valueOf(subscriptionType);
        for(int i = 0; i < number.length(); i++) {
            int userSubType = Character.digit(number.charAt(i), 10);
            if(userSubType == 1) {
                removeAds = true;
            } else if(userSubType == 2) {
                playPremium = true;
            } else if(userSubType == 3) {
                downloadPremium = true;
            } else {
                removeAds = false;
                playPremium = false;
                downloadPremium = false;
            }
        }
    }

    void topMoviesImageSlider() {
        movieImageSliderMaxVisible= 1;
        //if(movieImageSliderMaxVisible > 0) {
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject jsonObject = new JSONObject();
            try {
                // Populate JSON object with provided data
                jsonObject.put("email", "admin@gmail.com");
                jsonObject.put("password", "123456");
                jsonObject.put("usermode", "admin");
                jsonObject.put("caller", "webadmin");
                jsonObject.put("searchtype", "all");
                jsonObject.put("searchcontent", "");


                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/customslider/fetchcustomslider",
                        jsonObject, response -> {

                    if(!response.equals("No Data Avaliable")) {
                        JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                        if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")) {
                            JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                            //JsonArray jsonArray = new Gson().fromJson(response.toString(), JsonArray.class);
                            int i = 0;
                            int maxVisible = movieImageSliderMaxVisible;
                            for (JsonElement r : jsonArray) {
                                // if (i < maxVisible) {
                                JsonObject rootObject = r.getAsJsonObject();
                                int id = 0; // Default value in case "contentid" is not present
                                if (rootObject.has("contentid")) {
                                    id = rootObject.get("contentid").getAsInt();
                                }

                                String name = ""; // Default value in case "title" is not present
                                if (rootObject.has("title")) {
                                    name = rootObject.get("title").getAsString();
                                }

                                String banner = ""; // Default value in case "sliderimageurl" is not present
                                if (rootObject.has("sliderimageurl")) {
                                    banner = rootObject.get("sliderimageurl").getAsString();
                                }

                                String status = ""; // Default value in case "status" is not present
                                if (rootObject.has("status")) {
                                    status = rootObject.get("status").getAsString();
                                }
                                imageSliderItems.add(new ImageSliderItem(banner, name, 0, id, ""));
                            }
                            viewPager2.setVisibility(View.VISIBLE);
                            viewPager2.setAdapter(new ImageSliderAdepter(imageSliderItems, viewPager2));

                        }else{
                            viewPager2.setVisibility(View.GONE);
                        }



                    } else {

                    }
                }, error -> {
                    // Do nothing because There is No Error if error It will return 0
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                       // params.put("x-api-key", AppConfig.apiKey);
                        return params;
                    }

                };
                queue.add(jsonRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    void loadhomecontentlist() {

        topMoviesImageSlider();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JSONObject jsonObject = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObject.put("email", "admin@gmail.com");
            jsonObject.put("usermode", "admin");
            jsonObject.put("caller", "mobile");
            jsonObject.put("searchtype", "hometrending");
            jsonObject.put("searchcontent", "trending");


            JsonObjectRequest trendingRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {

                if (!response.equals("No Data Avaliable")) {

                    trendingLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")) {
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<TrendingList> trendingList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                            imageSliderItems.add(new ImageSliderItem(banner, name, 0, id, ""));

                            trendingList.add(new TrendingList(id, type, content_type, banner));
                        }

                        TrendingListAdepter myadepter = new TrendingListAdepter(context, trendingList);
                        home_trending_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        home_trending_list_Recycler_View.setAdapter(myadepter);

                        homeSwipeRefreshLayout.setRefreshing(false);
                    }else {
                        trendingLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    trendingLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }

            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    // params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }

            };
            queue.add(trendingRequest);


            jsonObject.put("searchtype", "contenttype");
            jsonObject.put("searchcontent", "Movie");
            JsonObjectRequest movieRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    homeMovieLayout.setVisibility(View.VISIBLE);

                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<MovieList> movieList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();

                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

//                        String poster = rootObject.get("poster").getAsString();
//                        int type = rootObject.get("type").getAsInt();
//                        int status = rootObject.get("status").getAsInt();
                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            // String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();

                            //if (status == 1) {
                            movieList.add(new MovieList(id, type, name, year, banner));
                            //}
                        }

                        //if (shuffleContents == 1) {
                        //    Collections.shuffle(movieList);
                       // }

                        MovieListAdepter myadepter = new MovieListAdepter(context, movieList);
                        movieListRecycleview.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        movieListRecycleview.setAdapter(myadepter);
                    }else{
                        homeMovieLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    homeMovieLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                   // params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(movieRequest);

            jsonObject.put("searchtype", "contenttype");
            jsonObject.put("searchcontent", "Web Series");
            JsonObjectRequest loveRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    homeWebSeriesLayout.setVisibility(View.VISIBLE);

                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<WebSeriesList> webSeriesList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            //String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();

                            // if (status == 1) {
                            webSeriesList.add(new WebSeriesList(id, type, name, year, banner));
                            //  }
                        }
                       // if (shuffleContents == 1) {
                       ///     Collections.shuffle(webSeriesList);
                       // }

                        WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, webSeriesList);
                        webSeriesListRecycleview.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        webSeriesListRecycleview.setAdapter(myadepter);

                        //homeSwipeRefreshLayout.setRefreshing(false);
                    }else {
                        homeWebSeriesLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    homeWebSeriesLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(loveRequest);

            jsonObject.put("searchtype", "viewcount");
            jsonObject.put("searchcontent", "Movie");
            JsonObjectRequest popularMovies = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    popularMoviesLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<MovieList> movieList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            movieList.add(new MovieList(id, type, name, year, banner));
                        }

                        Collections.shuffle(movieList);

                        moviesOnlyForYouListAdepter myadepter = new moviesOnlyForYouListAdepter(context, movieList);
                        home_popularMovies_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        home_popularMovies_list_Recycler_View.setAdapter(myadepter);

                        homeSwipeRefreshLayout.setRefreshing(false);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                    }else {
                        popularMoviesLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    popularMoviesLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(popularMovies);

            jsonObject.put("searchtype", "viewcount");
            jsonObject.put("searchcontent", "Web Series");
            JsonObjectRequest popularWebseries = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    popularMoviesLayout.setVisibility(View.VISIBLE);
                     JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<WebSeriesList> webSeriesList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                                webSeriesList.add(new WebSeriesList(id, type, name, year, banner));
                        }

                        Collections.shuffle(webSeriesList);

                        webSeriesOnlyForYouListAdepter myadepter = new webSeriesOnlyForYouListAdepter(context, webSeriesList);
                        home_popularWebSeries_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        home_popularWebSeries_list_Recycler_View.setAdapter(myadepter);

                        homeSwipeRefreshLayout.setRefreshing(false);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                    } else {
                        popularMoviesLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    popularMoviesLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(popularWebseries);

            jsonObject.put("searchtype", "categorytype");
            jsonObject.put("searchcontent", "Horror");
            JsonObjectRequest horrorContent = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    houseOfHorrorLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<HouseofHorrorList> houseofHorrorList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            houseofHorrorList.add(new HouseofHorrorList(id, type, name, year, banner));
                        }

                        Collections.shuffle(houseofHorrorList);

                        HouseOfHorrorListAdepter myadepter = new HouseOfHorrorListAdepter(context, houseofHorrorList);
                        house_of_horror_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        house_of_horror_list_Recycler_View.setAdapter(myadepter);

                        homeSwipeRefreshLayout.setRefreshing(false);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);

                    } else {
                        houseOfHorrorLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    houseOfHorrorLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(horrorContent);

            jsonObject.put("searchtype", "categorytype");
            jsonObject.put("searchcontent", "Old is Gold");
            JsonObjectRequest oldidgoldContent = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    oldisgoldLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<HouseofHorrorList> houseofHorrorList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            houseofHorrorList.add(new HouseofHorrorList(id, type, name, year, banner));
                        }

                        Collections.shuffle(houseofHorrorList);

                        HouseOfHorrorListAdepter myadepter = new HouseOfHorrorListAdepter(context, houseofHorrorList);
                        old_is_gold_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        old_is_gold_list_Recycler_View.setAdapter(myadepter);

                        homeSwipeRefreshLayout.setRefreshing(false);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);

                    } else {
                        oldisgoldLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    oldisgoldLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(oldidgoldContent);

            jsonObject.put("searchtype", "contenttype");
            jsonObject.put("searchcontent", "Music");
            JsonObjectRequest musicRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    musicLayout.setVisibility(View.VISIBLE);

                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<WebSeriesList> webSeriesList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            //String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();

                            // if (status == 1) {
                            webSeriesList.add(new WebSeriesList(id, type, name, year, banner));
                            //  }
                        }
                        // if (shuffleContents == 1) {
                        ///     Collections.shuffle(webSeriesList);
                        // }

                        WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, webSeriesList);
                        music_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        music_list_Recycler_View.setAdapter(myadepter);

                        //homeSwipeRefreshLayout.setRefreshing(false);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                    }else {
                        musicLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    musicLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(musicRequest);

            jsonObject.put("searchtype", "contenttype");
            jsonObject.put("searchcontent", "18+");
            JsonObjectRequest adultRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    adultLayout.setVisibility(View.VISIBLE);

                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<WebSeriesList> webSeriesList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            //String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();

                            // if (status == 1) {
                            webSeriesList.add(new WebSeriesList(id, type, name, year, banner));
                            //  }
                        }
                        // if (shuffleContents == 1) {
                        ///     Collections.shuffle(webSeriesList);
                        // }

                        WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, webSeriesList);
                        adult_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        adult_list_Recycler_View.setAdapter(myadepter);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }else {
                        adultLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }


                } else {
                    adultLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(adultRequest);

            jsonObject.put("searchtype", "recentlyadded");
            jsonObject.put("searchcontent", "Movie");
            JsonObjectRequest recentlyContent = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    recentMoviesLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<MovieList> recentlyAddedMovieList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            recentlyAddedMovieList.add(new MovieList(id, type, name, year, banner));
                        }

                        Collections.shuffle(recentlyAddedMovieList);

                        MovieListAdepter myadepter = new MovieListAdepter(context, recentlyAddedMovieList);
                        home_Recent_Movies_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        home_Recent_Movies_list_Recycler_View.setAdapter(myadepter);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                        homeSwipeRefreshLayout.setRefreshing(false);

                    } else {
                        recentMoviesLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    recentMoviesLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(recentlyContent);

            jsonObject.put("searchtype", "recentlyadded");
            jsonObject.put("searchcontent", "Web Series");
            JsonObjectRequest recentlyWebseriesContent = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {
                if (!response.equals("No Data Avaliable")) {
                    recentMoviesLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")){
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<WebSeriesList> recentlyAddedWebSeriesList = new ArrayList<>();
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String year = "";
                            if (!rootObject.get("releasedate").getAsString().equals("")) {
                                year = helperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }

                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 1;
                            recentlyAddedWebSeriesList.add(new WebSeriesList(id, type, name, year, banner));
                        }

                        Collections.shuffle(recentlyAddedWebSeriesList);

                        WebSeriesListAdepter myadepter = new WebSeriesListAdepter(context, recentlyAddedWebSeriesList);
                        home_Recent_Series_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        home_Recent_Series_list_Recycler_View.setAdapter(myadepter);
                        shimmer_view_container.stopShimmer();
                        shimmer_view_container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                        homeSwipeRefreshLayout.setRefreshing(false);

                    } else {
                        recentWebSeriesLayout.setVisibility(View.GONE);
                        homeSwipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    recentWebSeriesLayout.setVisibility(View.GONE);
                    homeSwipeRefreshLayout.setRefreshing(false);
                }
            }, error -> {
                // Do nothing because There is No Error if error It will return 0
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //params.put("x-api-key", AppConfig.apiKey);
                    return params;
                }
            };
            queue.add(recentlyWebseriesContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ResumeContentDatabase db = ResumeContentDatabase.getDbInstance(context.getApplicationContext());
        List<ResumeContent> resumeContents = db.resumeContentDao().getResumeContents();

        if (resumeContents.isEmpty()) {
            resume_Layout.setVisibility(View.GONE);
        } else {
            resume_Layout.setVisibility(View.VISIBLE);
        }

        List<ResumeContent> mData = resumeContents;
        List<ContinuePlayingList> continuePlayingList = new ArrayList<>();

        for (int i = 0; i < mData.size(); i++) {

            int id = mData.get(i).getId();
            int contentID = mData.get(i).getContent_id();

            String contentType = mData.get(i).getContent_type();

            String name = mData.get(i).getName();

            String year = "";
            if (!mData.get(i).getYear().equals("")) {
                year = HelperUtils.getYearFromDate(mData.get(i).getYear());
            }
            String poster = mData.get(i).getPoster();
            String sourceType = mData.get(i).getSource_type();
            String sourceUrl = mData.get(i).getSource_url();
            int type = mData.get(i).getType();
            long position = mData.get(i).getPosition();
            long duration = mData.get(i).getDuration();

            continuePlayingList.add(new ContinuePlayingList(id, contentID, name, year, poster, sourceType, sourceUrl, type, contentType, position, duration));

            ContinuePlayingListAdepter myadepter = new ContinuePlayingListAdepter(context, continuePlayingList);
            continuePlaying_list_Recycler_View.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
            continuePlaying_list_Recycler_View.setAdapter(myadepter);

        }

    }

    private void loadAd() {

        if (adType == 1) {   //AdMob
            admobNativeadTemplateLayout.setVisibility(View.VISIBLE);
            adViewLayout.setVisibility(View.GONE);

            //Home Header Native Ad
            MobileAds.initialize(context);
            AdLoader adLoader = new AdLoader.Builder(context, AppConfig.adMobNative)
                    .forNativeAd(nativeAd -> {
                        template.setNativeAd(nativeAd);
                    })
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());

            //Home Footer Banner Ad
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(AppConfig.adMobBanner);
            (bannerViewLayout).addView(mAdView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        } else if (adType == 2) { // StartApp
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            // Define StartApp Banner
            Banner startAppBanner = new Banner(context);
            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // Add to main Layout
            adViewLayout.addView(startAppBanner, bannerParameters);

            // Define StartApp Mrec
            Mrec startAppMrec = new Mrec(context);
            RelativeLayout.LayoutParams mrecParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            // Add to main Layout
            bannerViewLayout.addView(startAppMrec, mrecParameters);

        } else if (adType == 3) { //Facebook
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            AudienceNetworkAds.initialize(context);

            //Home Header Banner Ad
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, AppConfig.facebook_banner_ads_placement_id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            adViewLayout.addView(adView);
            adView.loadAd();

            //Home Footer Banner Ad
            com.facebook.ads.AdView adViewFooter = new com.facebook.ads.AdView(context, AppConfig.facebook_banner_ads_placement_id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            bannerViewLayout.addView(adViewFooter);
            adViewFooter.loadAd();

        } else if(adType == 4) { //AdColony
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            String[] AdColony_AD_UNIT_Zone_Ids = new String[] {AppConfig.AdColony_BANNER_ZONE_ID,AppConfig.AdColony_INTERSTITIAL_ZONE_ID};
            AdColony.configure(getActivity(), AppConfig.AdColony_APP_ID, AdColony_AD_UNIT_Zone_Ids);

            AdColonyAdViewListener listener = new AdColonyAdViewListener() {
                @Override
                public void onRequestFilled(AdColonyAdView adColonyAdView) {
                    adViewLayout.addView(adColonyAdView);
                }
            };
            AdColony.requestAdView(AppConfig.AdColony_BANNER_ZONE_ID, listener, AdColonyAdSize.BANNER);
        } else if(adType == 5) { //Unityads
            admobNativeadTemplateLayout.setVisibility(View.GONE);

            UnityAds.initialize (getActivity(), AppConfig.Unity_Game_ID, false);

            BannerView bannerView = new BannerView(getActivity(), AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView.load();
            adViewLayout.addView(bannerView);

            BannerView bannerView1 = new BannerView(getActivity(), AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView1.load();
            bannerViewLayout.addView(bannerView1);
        } else if(adType == 6) { //Custom Ads
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            adViewLayout.setVisibility(View.GONE);
            bannerViewLayout.setVisibility(View.GONE);

            if(!AppConfig.Custom_Banner_url.equals("")) {
                if(AppConfig.Custom_Banner_url.toLowerCase().contains(".mp4")
                        || AppConfig.Custom_Banner_url.toLowerCase().contains(".mkv")) {

                    custom_banner_video_ad.setVisibility(View.VISIBLE);
                    custom_banner_video_ad.setUseController(false);
                    custom_banner_video_ad.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    ExoPlayer player = new ExoPlayer.Builder(context).build();
                    custom_banner_video_ad.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(AppConfig.Custom_Banner_url);
                    player.setMediaItem(mediaItem);
                    player.setVolume(0);
                    player.setRepeatMode(Player.REPEAT_MODE_ONE);
                    player.prepare();
                    player.play();
                } else {
                    custom_banner_ad.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(AppConfig.Custom_Banner_url)
                            .into(custom_banner_ad);
                }

                custom_banner_ad.setOnClickListener(view -> {
                    if(!AppConfig.Custom_Banner_click_url.equals("")) {
                        switch (AppConfig.Custom_Banner_click_url_type) {
                            case 1:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.Custom_Banner_click_url)));
                                break;
                            case 2:
                                Intent intent = new Intent(context, WebView.class);
                                intent.putExtra("URL", AppConfig.Custom_Banner_click_url);
                                startActivity(intent);
                                break;
                            default:
                        }
                    }
                });
            }

            if(!AppConfig.Custom_Banner_url.equals("")) {
                if(AppConfig.Custom_Banner_url.toLowerCase().contains(".mp4")
                        || AppConfig.Custom_Banner_url.toLowerCase().contains(".mkv")) {

                    custom_footer_banner_video_ad.setVisibility(View.VISIBLE);
                    custom_footer_banner_video_ad.setUseController(false);
                    custom_footer_banner_video_ad.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    ExoPlayer player = new ExoPlayer.Builder(context).build();
                    custom_footer_banner_video_ad.setPlayer(player);
                    MediaItem mediaItem = MediaItem.fromUri(AppConfig.Custom_Banner_url);
                    player.setMediaItem(mediaItem);
                    player.setVolume(0);
                    player.setRepeatMode(Player.REPEAT_MODE_ONE);
                    player.prepare();
                    player.play();
                } else {
                    custom_footer_banner_ad.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(AppConfig.Custom_Banner_url)
                            .into(custom_footer_banner_ad);
                }


                custom_footer_banner_ad.setOnClickListener(view -> {
                    if(!AppConfig.Custom_Banner_click_url.equals("")) {
                        switch (AppConfig.Custom_Banner_click_url_type) {
                            case 1:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.Custom_Banner_click_url)));
                                break;
                            case 2:
                                Intent intent = new Intent(context, WebView.class);
                                intent.putExtra("URL", AppConfig.Custom_Banner_click_url);
                                startActivity(intent);
                                break;
                            default:
                        }
                    }
                });
            }
        } else if(adType == 7) { // AppLovin Ads
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
            AppLovinSdk.initializeSdk( context, configuration -> {
                MaxAdView adView = new MaxAdView( AppConfig.applovin_Banner_ID, context);
                adViewLayout.addView(adView);
                adView.loadAd();

                MaxAdView adView1 = new MaxAdView( AppConfig.applovin_Banner_ID, context);
                bannerViewLayout.addView(adView1);
                adView1.loadAd();
            });
        } else if(adType == 8) { // IronSource Ads
            IronSource.init(getActivity(), AppConfig.ironSource_app_key, IronSource.AD_UNIT.OFFERWALL, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);

            IronSourceBannerLayout banner = IronSource.createBanner(getActivity(), ISBannerSize.BANNER);
            adViewLayout.addView(banner);
            IronSource.loadBanner(banner);

            IronSourceBannerLayout banner1 = IronSource.createBanner(getActivity(), ISBannerSize.BANNER);
            bannerViewLayout.addView(banner1);
            IronSource.loadBanner(banner1);

        } else {
            ad_container.setVisibility(View.GONE);
            admobNativeadTemplateLayout.setVisibility(View.GONE);
            bannerViewLayout.setVisibility(View.GONE);
        }

    }

}
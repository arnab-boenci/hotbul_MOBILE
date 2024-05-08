package com.dooo.android;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cj.videoprogressview.LightProgressView;
import com.cj.videoprogressview.VolumeProgressView;
import com.dooo.android.adepter.CastAdepter;
import com.dooo.android.adepter.CommentListAdepter;
import com.dooo.android.adepter.DownloadLinkListAdepter;
import com.dooo.android.adepter.PlayMovieItemListAdepter;
import com.dooo.android.adepter.PopularSearchListAdepter;
import com.dooo.android.adepter.ReletedMovieListAdepter;
import com.dooo.android.list.CastList;
import com.dooo.android.list.CommentList;
import com.dooo.android.list.DownloadLinkList;
import com.dooo.android.list.MovieList;
import com.dooo.android.list.PlayMovieItemIist;
import com.dooo.android.list.SearchList;
import com.dooo.android.utils.FullSrceen;
import com.dooo.android.utils.HTML5WebView;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.LoadingDialog;
import com.dooo.android.utils.PlayerUtils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.jetradarmobile.snowfall.SnowfallView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppAd;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

import org.imaginativeworld.oopsnointernet.dialogs.signal.DialogPropertiesSignal;
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import jp.wasabeef.glide.transformations.BlurTransformation;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class ContentInCategory extends AppCompatActivity {
    Context context;
    int id;
    int userId;
    int adType;
    RelativeLayout adViewLayout;
    private boolean vpnStatus;
    private HelperUtils helperUtils;
    boolean removeAds = false;
    String userData = null;
    String tempUserID = null;
    LoadingDialog loadingDialog;
    Handler customIntertialHandler;
    TextView categoryID;
    ImageView backArrow;
    SwipeRefreshLayout swiperefresh;
    LinearLayout brokenHartLayout;
    RelativeLayout mainMayoutStremNInCategoty;
    RelativeLayout toolbar;
    RecyclerView allStreamItInCategory;
    String cSearchType;
    String cName;
    String cType;
    List<Object> modelBase = new ArrayList<>();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(customIntertialHandler != null) {
            customIntertialHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppConfig.FLAG_SECURE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE);
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.Home_TitleBar_BG));

        Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.comment_tag_bg);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(AppConfig.primeryThemeColor));

        setContentView(R.layout.activity_tv_in_category);
        allStreamItInCategory = findViewById(R.id.allStreamItInCategory);
        categoryID = findViewById(R.id.categoryID);
        backArrow = findViewById(R.id.backArrow);
        swiperefresh = findViewById(R.id.swiperefresh);
        brokenHartLayout = findViewById(R.id.broken_hart_layout);
        mainMayoutStremNInCategoty = findViewById(R.id.main_mayout_strem_it_n_in_categoty);
        toolbar = findViewById(R.id.toolbar);

        Intent callingIntent = getIntent();
        cSearchType = callingIntent.getStringExtra("cSearchType");
        cName = callingIntent.getStringExtra("cName");
        cType = callingIntent.getStringExtra("cType");
        if (cType.isEmpty()) {
            return;
        }

        loadingDialog = new LoadingDialog(this);

        if(!AppConfig.allowVPN) {
            //check vpn connection
            helperUtils = new HelperUtils(ContentInCategory.this);
            vpnStatus = helperUtils.isVpnConnectionAvailable();
            if (vpnStatus) {
                helperUtils.showWarningDialog(ContentInCategory.this, "VPN!", "You are Not Allowed To Use VPN Here!", R.raw.network_activity_icon);
            }
        }

        context = this;

        loadConfig();

        loadData();

        if(userData != null) {
            tempUserID = String.valueOf(userId);
        } else {
            tempUserID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        Intent intent = getIntent();
        id = intent.getExtras().getInt("ID");

        if(userData != null) {
            HelperUtils.setViewLog(context, String.valueOf(userId), id, 1, AppConfig.apiKey);
        } else {
            HelperUtils.setViewLog(context, tempUserID, id,1, AppConfig.apiKey);
        }

        categoryID.setText(cName);
        backArrow.setOnClickListener(v -> finish());
        swiperefresh.setOnRefreshListener(() -> {
            swiperefresh.setRefreshing(true);
            if (!modelBase.isEmpty()) {
                modelBase.clear();
            }
            getRelatedCategory();
        });
        getRelatedCategory();
        // No Internet Dialog: Signal
        NoInternetDialogSignal.Builder builder = new NoInternetDialogSignal.Builder(
                this,
                getLifecycle()
        );
        DialogPropertiesSignal properties = builder.getDialogProperties();
        // Optional
        properties.setConnectionCallback(hasActiveConnection -> {
            // ...
        });
        properties.setCancelable(true); // Optional
        properties.setNoInternetConnectionTitle("No Internet"); // Optional
        properties.setNoInternetConnectionMessage("Check your Internet connection and try again"); // Optional
        properties.setShowInternetOnButtons(true); // Optional
        properties.setPleaseTurnOnText("Please turn on"); // Optional
        properties.setWifiOnButtonText("Wifi"); // Optional
        properties.setMobileDataOnButtonText("Mobile data"); // Optional

        properties.setOnAirplaneModeTitle("No Internet"); // Optional
        properties.setOnAirplaneModeMessage("You have turned on the airplane mode."); // Optional
        properties.setPleaseTurnOffText("Please turn off"); // Optional
        properties.setAirplaneModeOffButtonText("Airplane mode"); // Optional
        properties.setShowAirplaneModeOffButtons(true); // Optional
        builder.build();


    }

    void getRelatedCategory() {
        LinearLayoutCompat reletedContentLayout = findViewById(R.id.reletedContentLayout);
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObject.put("email", "admin@gmail.com");
            jsonObject.put("usermode", "admin");
            jsonObject.put("caller", "mobile");
            jsonObject.put("searchtype", cSearchType);
            jsonObject.put("searchcontent", cType);


            JsonObjectRequest trendingRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {

                if(!response.equals("No Data Avaliable")) {
                   // reletedContentLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")) {
                        if (jsonObjectResponse.has("resultList")) {
                            JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                            List<SearchList> searchList = new ArrayList<>();
                            for (JsonElement r : jsonArray) {
                                JsonObject rootObject = r.getAsJsonObject();
                                int id = rootObject.get("videocontentid").getAsInt();
                                String name = rootObject.get("title").getAsString();
                                String banner = rootObject.get("posterimageurl").getAsString();
                                int type = 0;
                                String year = "";
                                String content_type = "";
                                if (!rootObject.get("releasedate").getAsString().equals("")) {
                                    year = HelperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                                }
                                if (cSearchType.equalsIgnoreCase("categorytype")) {
                                    content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                                } else if (cSearchType.equalsIgnoreCase("contenttype")) {
                                    content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                                }else {
                                    content_type = rootObject.get("contenttypename").getAsString();
                                }

                                searchList.add(new SearchList(id, type, name, year, banner, content_type));
                            }
                            PopularSearchListAdepter myadepter = new PopularSearchListAdepter(context, searchList);
                            allStreamItInCategory.setLayoutManager(new GridLayoutManager(context, 2));
                            allStreamItInCategory.setAdapter(myadepter);
                            swiperefresh.setRefreshing(false);
                            if (!searchList.isEmpty()) {
                                brokenHartLayout.setVisibility(View.GONE);
                            }
                        }
                    }

                } else {
                   // reletedContentLayout.setVisibility(View.GONE);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        if (sharedPreferences.getString("UserData", null) != null) {
            userData = sharedPreferences.getString("UserData", null);
            JsonObject jsonObject = new Gson().fromJson(userData, JsonObject.class);
            userId = jsonObject.get("userdetailsid").getAsInt();
        }

    }

    private void loadConfig() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
        String config = sharedPreferences.getString("Config", null);
        JsonObject jsonObject = new Gson().fromJson(config, JsonObject.class);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(!AppConfig.allowVPN) {
            //check vpn connection
            helperUtils = new HelperUtils(ContentInCategory.this);
            vpnStatus = helperUtils.isVpnConnectionAvailable();
            if (vpnStatus) {
                helperUtils.showWarningDialog(ContentInCategory.this, "VPN!", "You are Not Allowed To Use VPN Here!", R.raw.network_activity_icon);
            }
        }
    }
}
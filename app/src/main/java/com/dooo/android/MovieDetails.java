package com.dooo.android;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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

import android.app.Activity;
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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
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
import com.dooo.android.adepter.ReletedMovieListAdepter;
import com.dooo.android.adepter.TrendingListAdepter;
import com.dooo.android.list.CastList;
import com.dooo.android.list.DownloadLinkList;
import com.dooo.android.list.ImageSliderItem;
import com.dooo.android.list.MovieList;
import com.dooo.android.list.PlayMovieItemIist;
import com.dooo.android.list.CommentList;
import com.dooo.android.list.TrendingList;
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
import com.google.android.exoplayer2.ext.cast.CastPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
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
import com.google.android.gms.cast.framework.CastContext;
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
import com.unity3d.services.banners.BannerErrorInfo;
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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.glide.transformations.BlurTransformation;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.VideoView;

public class MovieDetails extends AppCompatActivity {
    Context context;

    int id;

    int userId;

    String trailerUrl;

    int contentId;
    String name;
    String releaseDate;
    String runtime;
    String genres;
    String poster;
    String banner;
    int downloadable;
    int type;

    String trailertype;
    int status;
    String description;

    ImageView trailerIcon;
    ImageView favouriteIcon;
    ImageView downloadIcon;

    Boolean isFavourite = false;

    int adType;

    RelativeLayout adViewLayout;

    private boolean vpnStatus;
    private HelperUtils helperUtils;

    boolean removeAds = false;
    boolean playPremium = false;
    boolean downloadPremium = false;

    View rootView;

    String userData = null;

    String tempUserID = null;

    LoadingDialog loadingDialog;

    int TMDB_ID;
    Handler customIntertialHandler;

    StyledPlayerView simpleExoPlayerView;
    String trailerextention = "";
    String mediaUrl;
    RelativeLayout lPlay;
    int playerHeight;
    AudioManager mAudioManager;
    ImageView movieDetailsBack;
    ImageView movieDetailsBanner;
    // gester
    GestureDetector mGestureDetector;
    protected int mStreamVolume;
    protected float mBrightness;
    LightProgressView mLightPeogressView;
    VolumeProgressView mVolumeProgressView;
    private boolean mChangeBrightness;
    private boolean mChangeVolume;

    FrameLayout webViewEm;
    HTML5WebView mWebView;
    RelativeLayout weblPlay;
    ExoPlayer player;
    View playerLayout;
    ProgressBar progressBar;
    LinearLayout exoRewind;
    LinearLayout exoForward;
    LinearLayout seekbarLayout;
    TextView liveTv;
    long currentPlayPostion = 0;
    //new Update  Player
    VideoView player1;
    YouTubePlayerView youTubePlayerView;
    private boolean isFullscreen = false;
    MediaSource mediaSource = null;
    boolean isPlaying;
    ImageView exoPlay,exoPause,backArowPlayer,aspectRatioIv,aspectRepeate,screenLock,screenUnlock;
    int aspectClickCount = 1;
    LinearLayout playpusLayout;
    LinearLayout seckBarInPlayer;
    RelativeLayout titleBarinPlayer;
    LinearLayout bottomLayout;
    RelativeLayout volumeRelativeLayout;
    RelativeLayout titleBartwo;
    ImageView lockOriLandscape;
    ImageView weblockOriLandscape;
    ImageView lockOriPortrait;
    ImageView weblockOriPortrait;
    boolean isFullScr;
    boolean fullScreenByClick;
    boolean activeMovie;
    RelativeLayout mainlayouthome;
    boolean webisVideo = true;
    boolean isVideo = true;
    TextView videoTileIdController;
    int id1 = 0;
    String movieName = "";
    String size = "";
    String quality = "";
    int movieId = id;
    String url = "";
    String conetnttype = "mp4"; //rootObject.getAsJsonObject("videoContent").getAsJsonObject("contentType").get("contenttypename").getAsString();
   // String status = "";
    int skipAvailable = 1;//rootObject.get("skip_available").getAsInt();
    String introStart = "";//rootObject.get("intro_start").getAsString();
    String introEnd = "";//rootObject.get("intro_end").getAsString();
    int link_type = 0;//rootObject.get("link_type").getAsInt();
    String drm_uuid = "";//rootObject.get("drm_uuid").isJsonNull() ? "" : rootObject.get("drm_uuid").getAsString();
    String drm_license_uri = "";//rootObject.get("drm_license_uri").isJsonNull() ? "" : rootObject.get("drm_license_uri").getAsString();



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

        setContentView(R.layout.activity_movie_details_1);

        loadingDialog = new LoadingDialog(this);

        rootView = findViewById(R.id.movie_details);
        simpleExoPlayerView = findViewById(R.id.video_view);
        lPlay = findViewById(R.id.play);
        weblPlay = findViewById(R.id.webplay);
        playerLayout = findViewById(R.id.player_layout);
        progressBar = findViewById(R.id.progressBar);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        exoRewind = findViewById(R.id.rewind_layout);
        exoForward = findViewById(R.id.forward_layout);
        liveTv = findViewById(R.id.live_tv);
        seekbarLayout = findViewById(R.id.seekbar_layout);
        exoPlay = findViewById(R.id.exo_play);
        exoPause = findViewById(R.id.exo_pause);
        backArowPlayer = findViewById(R.id.backArowPlayer);
        aspectRatioIv = findViewById(R.id.aspect_ratio_iv);
        aspectRepeate = findViewById(R.id.aspect_repeate);
        screenLock = findViewById(R.id.screen_lock);
        screenUnlock = findViewById(R.id.screen_unlock);
        playpusLayout = findViewById(R.id.playpusLayout);
        bottomLayout = findViewById(R.id.bottom_layout);
        titleBarinPlayer = findViewById(R.id.titleBarinPlayer);
        volumeRelativeLayout = findViewById(R.id.volumeRelativeLayout);
        titleBartwo = findViewById(R.id.titleBartwo);
        seckBarInPlayer = findViewById(R.id.seckBarInPlayer);
        lockOriLandscape = findViewById(R.id.lockOriLandscape);
        weblockOriLandscape = findViewById(R.id.weblockOriLandscape);
        lockOriPortrait = findViewById(R.id.lockOriPortrait);
        weblockOriPortrait = findViewById(R.id.weblockOriPortrait);
        mainlayouthome = findViewById(R.id.main_layout_home);
        videoTileIdController = findViewById(R.id.videoTileIdController);

        getLifecycle().addObserver(youTubePlayerView);
        //new Player Update
        player1 = findViewById(R.id.player1);
        player1.setScreenScaleType(VideoView.SCREEN_SCALE_DEFAULT);
        mWebView = new HTML5WebView(this);

        if(!AppConfig.allowVPN) {
            //check vpn connection
            helperUtils = new HelperUtils(MovieDetails.this);
            vpnStatus = helperUtils.isVpnConnectionAvailable();
            if (vpnStatus) {
                helperUtils.showWarningDialog(MovieDetails.this, "VPN!", "You are Not Allowed To Use VPN Here!", R.raw.network_activity_icon);
            }
        }

        context = this;

        loadConfig();

        loadData();

        loadUserSubscriptionDetails();

        initView();


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

        favouriteIcon = findViewById(R.id.Favourite_Icon);
        downloadIcon = findViewById(R.id.Download_Icon);
        trailerIcon = findViewById(R.id.Trailer_Icon);

        movieDetailsBack =  findViewById(R.id.Movie_Details_Back);
      //  movieDetailsBack.setOnClickListener(view -> finish());
        backArowPlayer.setOnClickListener(v -> onBackPressed());
        movieDetailsBack.setOnClickListener(v -> {
            if (activeMovie) {
                setPlayerNormalScreen();
                if (player != null) {
                    player.setPlayWhenReady(false);
                    player.stop();
                }

                showDescriptionLayout();
                activeMovie = false;
            } else {
                finish();
            }
        });

        loadMovieDetails(id);

        playerHeight = lPlay.getLayoutParams().height;
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        playerHeight = lPlay.getLayoutParams().height;

        View trailerLayout = findViewById(R.id.Trailer_Layout);
        trailerLayout.setOnClickListener(view -> {
            if(!trailerUrl.equals("")) {
//                Intent intent1 = new Intent(MovieDetails.this, TrailerPlayer.class);
//                intent1.putExtra("Trailer_URL", trailerUrl);
//                startActivity(intent1);
                trailertype = trailerextention;
                mediaUrl = trailerUrl;
                url = trailerUrl;
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                  //  viewContPost(content_type, id);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                       // viewContPost(content_type, id);
                    }, 1000);

                  //  getUrlsListThisItem(id);

                }, 2000);
                lPlay.setVisibility(VISIBLE);
                movieDetailsBack.setVisibility(GONE);
                initVideoPlayer(mediaUrl, MovieDetails.this, trailertype);
                FullSrceen.hideSystemUI(getWindow());
            }
        });
        lockOriPortrait.setOnClickListener(v -> {
            lockOriPortrait.setVisibility(GONE);
            lockOriLandscape.setVisibility(VISIBLE);
            controlFullScreenPlayer();
        });

        lockOriLandscape.setOnClickListener(v -> {
            lockOriPortrait.setVisibility(VISIBLE);
            lockOriLandscape.setVisibility(GONE);
            controlFullScreenPlayer();
        });
        //Web Control
        weblockOriLandscape.setOnClickListener(v -> {
            lockOriPortrait.setVisibility(VISIBLE);
            lockOriLandscape.setVisibility(GONE);
            webViewcontrolFullScreenPlayer();
        });
        weblockOriPortrait.setOnClickListener(v -> {
            lockOriPortrait.setVisibility(GONE);
            lockOriLandscape.setVisibility(VISIBLE);
            webViewcontrolFullScreenPlayer();
        });

        screenLock.setOnClickListener(v -> {


            simpleExoPlayerView.hideController();
            playpusLayout.setVisibility(GONE);
            titleBartwo.setVisibility(GONE);
            bottomLayout.setVisibility(GONE);
            titleBarinPlayer.setVisibility(GONE);
            seckBarInPlayer.setVisibility(GONE);
            volumeRelativeLayout.setVisibility(GONE);
            screenLock.setVisibility(GONE);
            screenUnlock.setVisibility(VISIBLE);

        });

        screenUnlock.setOnClickListener(v -> {


            simpleExoPlayerView.showController();
            titleBartwo.setVisibility(VISIBLE);
            bottomLayout.setVisibility(VISIBLE);
            playpusLayout.setVisibility(VISIBLE);
            seckBarInPlayer.setVisibility(VISIBLE);
            titleBarinPlayer.setVisibility(VISIBLE);
            volumeRelativeLayout.setVisibility(VISIBLE);
            screenLock.setVisibility(VISIBLE);
            screenUnlock.setVisibility(GONE);

        });

        aspectRepeate.setOnClickListener(v -> {
            initVideoPlayer(mediaUrl, MovieDetails.this, trailertype);
        });

        exoPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player.isPlaying()){
                    player.pause();
                    exoPause.setVisibility(GONE);
                    exoPlay.setVisibility(VISIBLE);
                }

            }
        });


        exoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!player.isPlaying()){
                    player.play();
                    exoPlay.setVisibility(GONE);
                    exoPause.setVisibility(VISIBLE);
                }
            }
        });
        aspectRatioIv.setOnClickListener(view -> {
            if (aspectClickCount == 0) {
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                aspectClickCount = 1;
                aspectRatioIv.setImageResource(R.drawable.screen_3);

            } else if (aspectClickCount == 1) {
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                aspectClickCount = 2;
                aspectRatioIv.setImageResource(R.drawable.screen_4);

            } else if (aspectClickCount == 2) {
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                aspectClickCount = 3;
                aspectRatioIv.setImageResource(R.drawable.screen_5);

            } else if (aspectClickCount == 3) {
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                aspectClickCount = 4;
                aspectRatioIv.setImageResource(R.drawable.screen_0);

            } else if (aspectClickCount == 4) {
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                aspectClickCount = 0;
                aspectRatioIv.setImageResource(R.drawable.screen_2);

            }

        });

        View favouriteLayout = findViewById(R.id.Favourite_Layout);
        favouriteLayout.setOnClickListener(view -> {
            if(isFavourite) {
                removeFavourite();
            } else {
                setFavourite();
            }
        });


        if(AppConfig.safeMode) {
            findViewById(R.id.playLayout).setVisibility(View.INVISIBLE);
           // findViewById(R.id.downloadLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.playLayout).setVisibility(View.VISIBLE);
           // findViewById(R.id.downloadLayout).setVisibility(View.VISIBLE);
        }

        LinearLayout playMovie = findViewById(R.id.Play_Movie);
        playMovie.setOnClickListener(view -> {
            if(AppConfig.all_movies_type == 0) {
                if(type== 1) {

                    if (playPremium) {
                        loadStreamLinks(id);
                        //playMovieTab(true);
                    } else {
                        HelperUtils helperUtils = new HelperUtils(MovieDetails.this);
                        helperUtils.Buy_Premium_Dialog(MovieDetails.this, "Buy Premium!", "Buy Premium Subscription To Watch Premium Content", R.raw.rocket_telescope);
                    }

                } else {
                    loadStreamLinks(id);
                    //playMovieTab(true);
                }
            } else if(AppConfig.all_movies_type == 1) {
                loadStreamLinks(id);
                //playMovieTab(true);
            } else if(AppConfig.all_movies_type == 2) {
                if (playPremium) {
                    loadStreamLinks(id);
                    //playMovieTab(true);
                } else {
                    HelperUtils helperUtils = new HelperUtils(MovieDetails.this);
                    helperUtils.Buy_Premium_Dialog(MovieDetails.this, "Buy Premium!", "Buy Premium Subscription To Watch Premium Content", R.raw.rocket_telescope);
                }
            }
        });

        LinearLayout clickToHideMoviePlayTab = findViewById(R.id.Click_to_hide_movie_play_tab);
        clickToHideMoviePlayTab.setOnClickListener(view -> playMovieTab(false));

        //Ad Controller
        if(!removeAds) {
            loadAd();
        }


        ConstraintLayout shareImgBtn = findViewById(R.id.Share_IMG_Btn);
        shareImgBtn.setOnClickListener(view -> {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_app_text));
            startActivity(Intent.createChooser(sharingIntent, "Share app via"));
        });

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

        ConstraintLayout downloadLayout = findViewById(R.id.downloadLayout);
        downloadLayout.setOnClickListener(v -> {
            if(downloadable == 1) {
                if(AppConfig.all_movies_type == 0) {
                    if(type== 1) {

                        if (downloadPremium) {
                            showDownloadOption(id);
                        } else {
                            HelperUtils helperUtils = new HelperUtils(MovieDetails.this);
                            helperUtils.Buy_Premium_Dialog(MovieDetails.this, "Buy Premium!", "Buy Premium Subscription To Download Premium Content", R.raw.rocket_telescope);
                        }

                    } else {
                        showDownloadOption(id);
                    }
                } else if(AppConfig.all_movies_type == 1) {
                    showDownloadOption(id);
                } else if(AppConfig.all_movies_type == 2) {
                    if (downloadPremium) {
                        showDownloadOption(id);
                    } else {
                        HelperUtils helperUtils = new HelperUtils(MovieDetails.this);
                        helperUtils.Buy_Premium_Dialog(MovieDetails.this, "Buy Premium!", "Buy Premium Subscription To Download Premium Content", R.raw.rocket_telescope);
                    }
                }
            }
        });

        LinearLayout reportButtonLinearLayout= findViewById(R.id.reportButton);
        reportButtonLinearLayout.setOnClickListener(view -> {
            if(userData != null) {
                final Dialog dialog = new Dialog(MovieDetails.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.report_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);

                ImageView dialogClose = (ImageView) dialog.findViewById(R.id.Coupan_Dialog_Close);
                dialogClose.setOnClickListener(v -> dialog.dismiss());

                EditText titleEditText = dialog.findViewById(R.id.titleEditText);
                titleEditText.setText(name);

                EditText descriptionEditText = dialog.findViewById(R.id.descriptionEditText);

                Button submitBtnButton = dialog.findViewById(R.id.submitBtn);
                submitBtnButton.setOnClickListener(btnView -> {
                    RequestQueue queue = Volley.newRequestQueue(this);
                    StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "createReport", response -> {
                        if (Boolean.valueOf(response)) {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(rootView, "Report Successfully Submited!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v -> snackbar.dismiss());
                            snackbar.show();
                        } else {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(rootView, "Error: Something went Wrong!", Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Close", v -> snackbar.dismiss());
                            snackbar.show();
                        }
                    }, error -> {
                        // Do nothing because There is No Error if error It will return 0
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(userId));
                            params.put("title", titleEditText.getText().toString());
                            params.put("description", descriptionEditText.getText().toString());
                            params.put("report_type", String.valueOf(1));
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("x-api-key", AppConfig.apiKey);
                            return params;
                        }
                    };
                    queue.add(sr);
                });
                dialog.show();
            } else {
                Snackbar snackbar = Snackbar.make(rootView, "Login to Report Content!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Login", v -> {
                    Intent lsIntent = new Intent(MovieDetails.this, LoginSignup.class);
                    startActivity(lsIntent);
                });
                snackbar.show();
            }
        });

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor));
    }

    public void showDescriptionLayout() {
        lPlay.setVisibility(GONE);
    }

    public void setPlayerNormalScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void controlFullScreenPlayer() {

//        currentPlayPostion = player.getCurrentPosition();
//        Intent intent = new Intent(MovieDetails.this, com.dooo.android.Player.class);
//        intent.putExtra("contentID", id1);
//        intent.putExtra("SourceID", id1);
//        intent.putExtra("Content_Type", "Movie");
//        intent.putExtra("name", movieName);
//        intent.putExtra("source", "mp4");
//        intent.putExtra("url", url);
//
//        intent.putExtra("DrmUuid", "");
//        intent.putExtra("DrmLicenseUri", "");
//
//        intent.putExtra("skip_available", 1);
//        intent.putExtra("intro_start", "");
//        intent.putExtra("intro_end", "");
//        intent.putExtra("current_position", currentPlayPostion);
//
//        startActivity(intent);

        if (isFullScr) {
            fullScreenByClick = false;
            isFullScr = false;
            activeMovie = false;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            lPlay.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.player_height);


//            if (isVideo) {
//                mainlayouthome.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));
//                lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT,
//                        RelativeLayout.LayoutParams.WRAP_CONTENT
//                );
//                //params.setMargins(0, 0, 0, 0);
//                //mainlayouthome.setLayoutParams(params);
//            }
            // reset the orientation
            lockOriPortrait.setVisibility(GONE);
            lockOriLandscape.setVisibility(VISIBLE);


        } else {
            fullScreenByClick = true;
            isFullScr = true;
            activeMovie = true;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            lPlay.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
            if (isVideo) {

               // mainlayouthome.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
               // lPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                lockOriPortrait.setVisibility(VISIBLE);
                lockOriLandscape.setVisibility(GONE);
            }

           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            Log.d("fsffd", "onStart: 11");
//            if (player != null) {
//                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//            }

//            FullSrceen.hideSystemUI(getWindow());
//            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            getWindow().getDecorView().setSystemUiVisibility(flags);


        }
    }
    public void webViewcontrolFullScreenPlayer() {
        if (isFullScr) {
            fullScreenByClick = false;
            isFullScr = false;
            activeMovie = false;


            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            if (webisVideo) {

                mainlayouthome.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));
                weblPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, playerHeight));
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 0);
                mainlayouthome.setLayoutParams(params);
            }
            // reset the orientation
            lockOriPortrait.setVisibility(GONE);
            lockOriLandscape.setVisibility(VISIBLE);


        } else {
            fullScreenByClick = true;
            isFullScr = true;
            activeMovie = true;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            if (webisVideo) {

                mainlayouthome.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                weblPlay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                lockOriPortrait.setVisibility(VISIBLE);
                lockOriLandscape.setVisibility(GONE);
            }

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

            if (player != null) {
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            }

            FullSrceen.hideSystemUI(getWindow());
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
            flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(flags);


        }
    }

    public void initVideoPlayer(String url, Context context, String type) {

        progressBar.setVisibility(VISIBLE);
        if (url != null) {
            webViewEm = findViewById(R.id.webViewEm);
            weblPlay.setVisibility(GONE);
            mWebView.handleBack();
        } else {
            lPlay.setVisibility(VISIBLE);
            movieDetailsBack.setVisibility(GONE);
        }
        if (player != null) {
            player.stop();
            player.release();
        }
        playerLayout.setVisibility(VISIBLE);


        AdaptiveTrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(context, trackSelectionFactory);
        player = new ExoPlayer.Builder(context)
                .setTrackSelector(trackSelector)
                .build();
        Uri uri = Uri.parse(url);

        ///if (content_type.contains("video")) {
            showExoControlForTv();
       /// } else if (content_type.contains("series")) {
       ///     showExoControlForTv();
      //  } else {
      //      hideExoControlForTv();
      //  }

        switch (type) {
            case "hls":
                player1.setVisibility(VISIBLE);
                playerDK(String.valueOf(uri), name, false);
                break;
            case "youtube":
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        String id = extractVideoId(url);
                        youTubePlayer.loadVideo(id, 0);
                        playerLayout.setVisibility(GONE);
                        youTubePlayerView.setVisibility(View.VISIBLE);
                        youTubePlayer.play();
                    }
                });
                youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                    @Override
                    public void onYouTubePlayerEnterFullScreen() {
                        toggleFullscreen();
                    }

                    @Override
                    public void onYouTubePlayerExitFullScreen() {
                        toggleFullscreen();
                    }
                });
                break;
            case "ts":
            case "mpd":
                mediaSource = mediaSource(uri);
                player.prepare(mediaSource, true, false);
                simpleExoPlayerView.setPlayer(player);

                break;
            case "rtmp":
                mediaSource = rtmpMediaSource(uri);
                player.prepare(mediaSource, true, false);
                simpleExoPlayerView.setPlayer(player);
                break;
            case "offline":
                mediaSource = offlineMediaSource(uri);
                player.prepare(mediaSource, true, false);
                simpleExoPlayerView.setPlayer(player);

             //   break;
//            case "embed":
//                String newUrl = extractURLFromEmbedCode(url);
//                webisVideo = true;
//                openWebActivity(newUrl);
//                break;
        }
        if (type.equals("vimeo") || type.equals("daily motion")) {
           // webisVideo = true;
            //openWebActivity(url);
        }else {
            mediaSource = mediaSource(uri);
            player.prepare(mediaSource, true, false);
            simpleExoPlayerView.setPlayer(player);

        }

        player.setPlayWhenReady(true);
        player.seekTo(currentPlayPostion);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    isPlaying = true;
                    progressBar.setVisibility(View.GONE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                    isPlaying = false;
                } else if (playbackState == Player.STATE_BUFFERING) {
                    isPlaying = false;
                    progressBar.setVisibility(VISIBLE);
                } else {
                    // player paused in any state
                    isPlaying = false;
                }
            }
        });
    }

    private MediaSource rtmpMediaSource(Uri uri) {
        MediaSource videoSource = null;
        RtmpDataSourceFactory dataSourceFactory = new RtmpDataSourceFactory();

        videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri));

        return videoSource;
    }

    private MediaSource mediaSource(Uri uri) {

        Log.d("sfsdsdffs", "mediaSource: "+uri);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), getUserAgent());
        MediaItem mMediaItem = MediaItem.fromUri(uri);
        return new ProgressiveMediaSource.Factory(dataSourceFactory, new DefaultExtractorsFactory())
                .createMediaSource(mMediaItem);
    }

    private String getUserAgent() {
        StringBuilder result = new StringBuilder(64);
        result.append("Dalvik/");
        result.append(System.getProperty("java.vm.version"));
        result.append(" (Linux; U; Android ");

        String version = Build.VERSION.RELEASE;
        result.append(version.length() > 0 ? version : "1.0");

        if ("REL".equals(Build.VERSION.CODENAME)) {
            String model = Build.MODEL;
            if (model.length() > 0) {
                result.append("; ");
                result.append(model);
            }
        }

        String id = Build.ID;

        if (id.length() > 0) {
            result.append(" Build/");
            result.append(id);
        }

        result.append(")");
        return result.toString();
    }

    private MediaSource offlineMediaSource(Uri uri) {
        DataSpec dataSpec = new DataSpec(uri);
        final FileDataSource fileDataSource = new FileDataSource();
        try {
            fileDataSource.open(dataSpec);
        } catch (FileDataSource.FileDataSourceException e) {
            e.printStackTrace();
        }
        DataSource.Factory factory = () -> fileDataSource;


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), getUserAgent());
        MediaItem mMediaItem = MediaItem.fromUri(uri);
        return new ProgressiveMediaSource.Factory(dataSourceFactory, new DefaultExtractorsFactory())
                .createMediaSource(mMediaItem);
    }
    private void toggleFullscreen() {
        if (isFullscreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) youTubePlayerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            youTubePlayerView.setLayoutParams(params);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) youTubePlayerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            youTubePlayerView.setLayoutParams(params);
        }
        isFullscreen = !isFullscreen;
    }
    public static String extractVideoId(String url) {
        String videoId = null;
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|%2Fvideos%2F|%2Fvi%2F)[^#\\?\\&\\'\\\"\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            videoId = matcher.group();
        }
        return videoId;
    }

    public void playerDK(String url, String title, boolean live) {
        if (player1 != null) {
            player1.release();
        }
        if (youTubePlayerView != null) {
            youTubePlayerView.release();
        }
        assert player1 != null;
        player1.setUrl(url);
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent(title, live);
        player1.setVideoController(controller);
        player1.setKeepScreenOn(true);


        player1.start();


        player1.addOnStateChangeListener(new VideoView.OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
                if (playerState == VideoView.STATE_ERROR) {

                    //  findViewById(R.id.linearlayout111).setVisibility(View.VISIBLE);

                } else {

                    //  findViewById(R.id.linearlayout111).setVisibility(View.GONE);
                }

            }

            @Override
            public void onPlayStateChanged(int playState) {
                if (playState == VideoView.STATE_ERROR) {
                    //  findViewById(R.id.linearlayout111).setVisibility(View.VISIBLE);
                } else {
                    //  findViewById(R.id.linearlayout111).setVisibility(View.GONE);
                }


            }
        });

    }

    public void hideExoControlForTv() {
        exoRewind.setVisibility(GONE);
        exoForward.setVisibility(GONE);
        liveTv.setVisibility(VISIBLE);
        seekbarLayout.setVisibility(GONE);
    }

    public void showExoControlForTv() {
        exoRewind.setVisibility(VISIBLE);
        exoForward.setVisibility(VISIBLE);
        liveTv.setVisibility(GONE);
        seekbarLayout.setVisibility(VISIBLE);
        liveTv.setVisibility(GONE);

    }


    private void initView() {
        mLightPeogressView = findViewById(R.id.lpv);
        mVolumeProgressView = findViewById(R.id.vpv);
        mBrightness = PlayerUtils.scanForActivity(this).getWindow().getAttributes().screenBrightness;
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mBrightness = MovieDetails.this.getWindow().getAttributes().screenBrightness;
        slideToChangeBrightness(0);
        slideToChangeVolume(0);
    }

    protected class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            mStreamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            mBrightness = MovieDetails.this.getWindow().getAttributes().screenBrightness;
            mChangeBrightness = false;
            mChangeVolume = false;

            return true;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1 == null || e2 == null) return false;
            float deltaY = e1.getY() - e2.getY();
            if (mChangeBrightness) {
                slideToChangeBrightness(deltaY);
            } else if (mChangeVolume) {
                slideToChangeVolume(deltaY);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    protected void slideToChangeBrightness(float deltaY) {
        Window window = PlayerUtils.scanForActivity(this).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        int height = PlayerUtils.getScreenHeight(getApplicationContext(), false);
        if (mBrightness == -1.0f) mBrightness = 0.5f;
        float brightness = deltaY * 2 / height * 1.0f + mBrightness;
        if (brightness < 0) {
            brightness = 0f;
        }
        if (brightness > 1.0f) brightness = 1.0f;
        mLightPeogressView.setProgress(brightness);
        attributes.screenBrightness = brightness;
        window.setAttributes(attributes);

    }

    protected void slideToChangeVolume(float deltaY) {
        int streamMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int height = PlayerUtils.getScreenHeight(getApplicationContext(), false);
        float deltaV = deltaY * 2 / height * streamMaxVolume;
        float index = mStreamVolume + deltaV;
        if (index > streamMaxVolume) index = streamMaxVolume;
        if (index < 0) {
            index = 0;
        }
        mVolumeProgressView.setProgress(index / streamMaxVolume);

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) index, 0);
    }

    void setColorTheme(int color) {
        CardView playLayout = findViewById(R.id.playLayout);
        playLayout.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView castLayoutColorBar = findViewById(R.id.castLayoutColorBar);
        castLayoutColorBar.setBackgroundTintList(ColorStateList.valueOf(color));

        CardView reletedContentLayoutColorBar = findViewById(R.id.reletedContentLayoutColorBar);
        reletedContentLayoutColorBar.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    void loadAdditionalDetails(JsonObject jsonObject) {

        JsonArray castArray = jsonObject.getAsJsonArray("listContentCasts");

        LinearLayout ratingLayout = findViewById(R.id.ratingLayout);
        ratingLayout.setVisibility(View.GONE);
        TextView rating = findViewById(R.id.rating);
        rating.setText("4");

        TextView maxrating = findViewById(R.id.maxrating);
        maxrating.setText("5");

       // JsonArray jsonArray = new Gson().fromJson(jsonObject.get("cast").getAsJsonArray(), JsonArray.class);
        List<CastList> castList = new ArrayList<>();
        for (JsonElement r : castArray) {
            JsonObject rootObject = r.getAsJsonObject();
            String name = rootObject.getAsJsonObject("actorDetails").get("actorname").isJsonNull() ? "" : rootObject.getAsJsonObject("actorDetails").get("actorname").getAsString();
            String role = rootObject.getAsJsonObject("actorDetails").get("actortype").isJsonNull() ? "" : rootObject.getAsJsonObject("actorDetails").get("actortype").getAsString();
            String image = rootObject.getAsJsonObject("actorDetails").get("actorimageurl").isJsonNull() ? "" : rootObject.getAsJsonObject("actorDetails").get("actorimageurl").getAsString();
            long actorid = rootObject.getAsJsonObject("actorDetails").get("actordetailsid").isJsonNull() ? 0 : rootObject.getAsJsonObject("actorDetails").get("actordetailsid").getAsLong();

            castList.add(new CastList(name, role, image , actorid));
        }
        RecyclerView castListRecyclerView = findViewById(R.id.castListRecyclerView);
        CastAdepter myadepter = new CastAdepter(context, castList);
        castListRecyclerView.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
        castListRecyclerView.setAdapter(myadepter);

        if(!castList.isEmpty()) {
            LinearLayout castLayout = findViewById(R.id.castLayout);
            castLayout.setVisibility(View.VISIBLE);
        }
//
//        try {
//            RequestQueue queue = Volley.newRequestQueue(context);
//            StringRequest sr = new StringRequest(Request.Method.POST, "https://cloud.team-dooo.com/Dooo/IMDB/index.php", response -> {
//                if(!Objects.equals(response, "false")) {
//
//                }
//            }, error -> {
//
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("ctype", String.valueOf(1));
//                    params.put("tmdbid", String.valueOf(TMDB_ID));
//                    params.put("bGljZW5zZV9jb2Rl", AppConfig.bGljZW5zZV9jb2Rl);
//                    return params;
//                }
//            };
//            queue.add(sr);
//        } catch (Exception ignored){}
    }

    private void initComment() {
        LinearLayout commentBtn = findViewById(R.id.commentBtn);
        commentBtn.setVisibility(View.VISIBLE);
        commentBtn.setOnClickListener(view->{
            if(findViewById(R.id.comment_tab).getVisibility() == View.GONE) {
                commentTab(true);
               // loadComments();
            } else {
                commentTab(false);
            }
        });

        findViewById(R.id.commentTabExtraSpace).setOnClickListener(v1->commentTab(false));
        findViewById(R.id.commentTabClose).setOnClickListener(v1->commentTab(false));

        CardView sendComment = findViewById(R.id.sendComment);
        EditText commentEditText = findViewById(R.id.commentEditText);
        sendComment.setOnClickListener(view->{
            msgSending(true);
            if(userData != null) {
                if(!commentEditText.getText().toString().equals("")) {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest sr = new StringRequest(Request.Method.POST, AppConfig.url + "addComments", response -> {
                      //  loadComments();
                        commentEditText.setText("");
                    }, error -> {
                        msgSending(false);
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", String.valueOf(userId));
                            params.put("content_id", String.valueOf(id));
                            params.put("content_type", String.valueOf(1));
                            params.put("comment", commentEditText.getText().toString());
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("x-api-key", AppConfig.apiKey);
                            return params;
                        }
                    };
                    queue.add(sr);
                } else {
                    msgSending(false);
                }
            } else {
                msgSending(false);
                Toasty.warning(context, "Please Login to Comment Here!.", Toast.LENGTH_SHORT, true).show();
            }

        });

    }

    private void loadComments() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getComments/"+id+"/1", response -> {
            msgSending(false);
            if(!response.equals("No Data Avaliable")) {
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<CommentList> commentList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();

                    int cUserID = rootObject.get("userID").getAsInt();
                    String userName = rootObject.get("userName").getAsString();
                    String comment = rootObject.get("comment").getAsString();

                    commentList.add(new CommentList(cUserID, userName, comment));


                    RecyclerView commentRecylerview = findViewById(R.id.commentRecylerview);
                    CommentListAdepter myadepter = new CommentListAdepter(userId, context, commentList);
                    commentRecylerview.setLayoutManager(new GridLayoutManager(context, 1));
                    commentRecylerview.setAdapter(myadepter);
                    commentRecylerview.scrollToPosition(commentList.size() - 1);
                }
            }
        }, error -> {
            // Do nothing
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    private void msgSending(boolean bool) {
        CardView sendComment = findViewById(R.id.sendComment);
        ImageView msgSentIconImageView= findViewById(R.id.msgSentIcon);
        SpinKitView loadingMsgSent = findViewById(R.id.loadingMsgSent);
        if(bool) {
            sendComment.setClickable(false);
            msgSentIconImageView.setVisibility(View.GONE);
            loadingMsgSent.setVisibility(View.VISIBLE);
        } else {
            msgSentIconImageView.setVisibility(View.VISIBLE);
            loadingMsgSent.setVisibility(View.GONE);
            sendComment.setClickable(true);
        }
    }

    private void commentTab(boolean show) {
        View commentTab = findViewById(R.id.comment_tab);
        ViewGroup movieDetails = findViewById(R.id.movie_details);

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(500);
        transition.addTarget(R.id.comment_tab);

        TransitionManager.beginDelayedTransition(movieDetails, transition);
        commentTab.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        EditText searchContentEditText = findViewById(R.id.commentEditText);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (searchContentEditText.isFocused()) {
                Rect outRect = new Rect();
                searchContentEditText.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    searchContentEditText.clearFocus();

                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(event);
    }

    private void showDownloadOption(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"getMovieDownloadLinks/"+id, response -> {
            if(!response.equals("No Data Avaliable")) {
                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
                List<DownloadLinkList> downloadLinkList = new ArrayList<>();
                for (JsonElement r : jsonArray) {
                    JsonObject rootObject = r.getAsJsonObject();
                    int LinkID = rootObject.get("id").getAsInt();
                    String name = rootObject.get("name").getAsString();
                    String size = rootObject.get("size").getAsString();
                    String quality = rootObject.get("quality").getAsString();
                    int link_order = rootObject.get("link_order").getAsInt();
                    int movie_id = rootObject.get("movie_id").getAsInt();
                    String url = rootObject.get("url").getAsString();
                    String type = rootObject.get("type").getAsString();
                    String download_type = rootObject.get("download_type").getAsString();

                    downloadLinkList.add(new DownloadLinkList(LinkID, name, size, quality, link_order, movie_id, url, type, download_type));
                }

                final Dialog downloadDialog = new Dialog(MovieDetails.this);
                downloadDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                downloadDialog.setCancelable(false);
                downloadDialog.setContentView(R.layout.download_dialog);
                downloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                downloadDialog.setCanceledOnTouchOutside(true);

                ImageView coupanDialogClose = (ImageView) downloadDialog.findViewById(R.id.Coupan_Dialog_Close);
                coupanDialogClose.setOnClickListener(v -> downloadDialog.dismiss());

                RecyclerView downloadLinksRecylerView = (RecyclerView) downloadDialog.findViewById(R.id.downloadLinksRecylerView);
                DownloadLinkListAdepter myadepter = new DownloadLinkListAdepter(context,rootView, downloadDialog, downloadLinkList);
                downloadLinksRecylerView.setLayoutManager(new GridLayoutManager(context, 1));
                downloadLinksRecylerView.setAdapter(myadepter);

                downloadDialog.show();

            } else {
                Snackbar snackbar = Snackbar.make(rootView, "No Download Server Avaliable!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Close", v -> snackbar.dismiss());
                snackbar.show();
            }
        }, error -> {
            // Do nothing because There is No Error if error It will return 0
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    private void loadAd() {
        adViewLayout = findViewById(R.id.ad_View_Layout);

        if(adType == 1) {   //AdMob
            MobileAds.initialize(this, initializationStatus -> {
                // Do nothing
            });
            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(this, AppConfig.adMobInterstitial, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd.show(MovieDetails.this);

                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                }
            });

            //Banner ad
            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(AppConfig.adMobBanner);
            (adViewLayout).addView(mAdView);
            AdRequest bannerAdRequest = new AdRequest.Builder().build();
            mAdView.loadAd(bannerAdRequest);
        } else if(adType == 2) { //StartApp
            // and show interstitial ad
            StartAppAd.showAd(this);

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
        } else if(adType == 3) { //Facebook

            AudienceNetworkAds.initialize(context);
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(this, AppConfig.facebook_banner_ads_placement_id, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            adViewLayout.addView(adView);
            adView.loadAd();

            com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(this, AppConfig.facebook_interstitial_ads_placement_id);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {

                @Override
                public void onError(Ad ad, AdError adError) {

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    interstitialAd.show();
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }

                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {

                }
            };
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());


        } else if(adType == 4) { //AdColony
            String[] AdColony_AD_UNIT_Zone_Ids = new String[] {AppConfig.AdColony_BANNER_ZONE_ID,AppConfig.AdColony_INTERSTITIAL_ZONE_ID};
            AdColony.configure(this, AppConfig.AdColony_APP_ID, AdColony_AD_UNIT_Zone_Ids);

            AdColonyInterstitialListener listener1 = new AdColonyInterstitialListener() {
                @Override
                public void onRequestFilled(AdColonyInterstitial adColonyInterstitial) {
                    adColonyInterstitial.show();
                }
            };
            AdColony.requestInterstitial(AppConfig.AdColony_INTERSTITIAL_ZONE_ID, listener1);

            AdColonyAdViewListener listener = new AdColonyAdViewListener() {
                @Override
                public void onRequestFilled(AdColonyAdView adColonyAdView) {
                    adViewLayout.addView(adColonyAdView);
                }
            };
            AdColony.requestAdView(AppConfig.AdColony_BANNER_ZONE_ID, listener, AdColonyAdSize.BANNER);
        } else if(adType == 5) { //unityads
            IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String placementId) {
                    UnityAds.show(MovieDetails.this, AppConfig.Unity_rewardedVideo_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {

                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {

                        }
                    });
                }

                @Override
                public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

                }
            };
            UnityAds.initialize (this, AppConfig.Unity_Game_ID, false);
            BannerView bannerView = new BannerView(MovieDetails.this, AppConfig.Unity_Banner_ID, new UnityBannerSize(320, 50));
            bannerView.load();
            adViewLayout.addView(bannerView);
            UnityAds.load(AppConfig.Unity_rewardedVideo_ID, loadListener);
        } else if(adType == 6) { //Custom Ads
            adViewLayout.setVisibility(View.GONE);

            customIntertialHandler = new Handler(Looper.getMainLooper());
            customIntertialHandler.postDelayed(() -> {
                if(!AppConfig.Custom_Interstitial_url.equals("")) {
                    ConstraintLayout customIntertial_layout = findViewById(R.id.customIntertial_layout);
                    customIntertial_layout.setVisibility(View.VISIBLE);


                    ImageView customIntertial_ad = findViewById(R.id.customIntertial_ad);
                    PlayerView custom_intertial_video_ad = findViewById(R.id.custom_intertial_video_ad);

                    if(AppConfig.Custom_Interstitial_url.toLowerCase().contains(".mp4")
                            || AppConfig.Custom_Interstitial_url.toLowerCase().contains(".mkv")) {

                        custom_intertial_video_ad.setVisibility(View.VISIBLE);
                        custom_intertial_video_ad.setUseController(false);
                        custom_intertial_video_ad.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                        ExoPlayer player = new ExoPlayer.Builder(context).build();
                        custom_intertial_video_ad.setPlayer(player);
                        MediaItem mediaItem = MediaItem.fromUri(AppConfig.Custom_Interstitial_url);
                        player.setMediaItem(mediaItem);
                        player.setVolume(0);
                        player.setRepeatMode(com.google.android.exoplayer2.Player.REPEAT_MODE_ONE);
                        player.prepare();
                        player.play();

                    } else {
                        customIntertial_ad.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(AppConfig.Custom_Interstitial_url)
                                .into(customIntertial_ad);
                    }

                    ImageView customIntertial_close_btn = findViewById(R.id.customIntertial_close_btn);
                    customIntertial_close_btn.setOnClickListener(view -> {
                        customIntertial_layout.setVisibility(View.GONE);
                    });

                    customIntertial_ad.setOnClickListener(view -> {
                        if(!AppConfig.Custom_Banner_click_url.equals("")) {
                            switch (AppConfig.Custom_Interstitial_click_url_type) {
                                case 1:
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.Custom_Banner_click_url)));
                                    break;
                                case 2:
                                    Intent intent = new Intent(MovieDetails.this, WebView.class);
                                    intent.putExtra("URL", AppConfig.Custom_Interstitial_click_url);
                                    startActivity(intent);
                                    break;
                                default:
                            }
                        }
                    });
                }
            }, 2000);


            if(!AppConfig.Custom_Banner_url.equals("")) {
                ImageView custom_banner_ad = findViewById(R.id.custom_banner_ad);
                PlayerView custom_banner_video_ad = findViewById(R.id.custom_banner_video_ad);

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
                                Intent intent = new Intent(MovieDetails.this, WebView.class);
                                intent.putExtra("URL", AppConfig.Custom_Banner_click_url);
                                startActivity(intent);
                                break;
                            default:
                        }
                    }
                });
            }
        } else if(adType == 7) { // AppLovin Ads
            AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
            AppLovinSdk.getInstance(context).getSettings().setTestDeviceAdvertisingIds(Arrays.asList("b4d923d0-7f39-41a4-a3cb-0f16ce8e3058"));
            AppLovinSdk.initializeSdk( context, configuration -> {
                MaxAdView adView = new MaxAdView( AppConfig.applovin_Banner_ID, this );
                adViewLayout.addView(adView);
                adView.loadAd();

                MaxInterstitialAd interstitialAd = new MaxInterstitialAd( AppConfig.applovin_Interstitial_ID, this );
                interstitialAd.loadAd();
                interstitialAd.setListener(new MaxAdListener() {
                    int retryAttempt = 0;
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        retryAttempt = 0;
                        interstitialAd.showAd();
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        retryAttempt++;
                        long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                        new Handler().postDelayed( new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                interstitialAd.loadAd();
                            }
                        }, delayMillis );
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        interstitialAd.loadAd();
                    }
                });
            });
        } else if(adType == 8) { // IronSource Ads
            IronSource.init(this, AppConfig.ironSource_app_key, IronSource.AD_UNIT.OFFERWALL, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.REWARDED_VIDEO, IronSource.AD_UNIT.BANNER);

            IronSourceBannerLayout banner = IronSource.createBanner(MovieDetails.this, ISBannerSize.BANNER);
            adViewLayout.addView(banner);
            IronSource.loadBanner(banner);

            IronSource.loadInterstitial();
            IronSource.setInterstitialListener(new InterstitialListener() {
                @Override
                public void onInterstitialAdReady() {
                    IronSource.showInterstitial();
                }

                @Override
                public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {

                }

                @Override
                public void onInterstitialAdOpened() {

                }

                @Override
                public void onInterstitialAdClosed() {

                }

                @Override
                public void onInterstitialAdShowSucceeded() {

                }

                @Override
                public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {

                }

                @Override
                public void onInterstitialAdClicked() {

                }
            });
        } else {
            adViewLayout.setVisibility(View.GONE);
        }
    }

    private void playMovieTab(boolean show) {
        View playMovieTab = findViewById(R.id.Play_Movie_Tab);
        ViewGroup movieDetails = findViewById(R.id.movie_details);
        TextView Play_Text = findViewById(R.id.Play_Text);
        Play_Text.setTextColor(Color.parseColor(AppConfig.primeryThemeColor));

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(600);
        transition.addTarget(R.id.Play_Movie_Tab);

        TransitionManager.beginDelayedTransition(movieDetails, transition);
        playMovieTab.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    void loadStreamLinks(int id) {
        loadingDialog.animate(true);

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonObjectRequest = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObjectRequest.put("email", "admin@gmail.com");
            jsonObjectRequest.put("usermode", "admin");
            jsonObjectRequest.put("caller", "mobile");
            jsonObjectRequest.put("searchtype", "videocontentid");
            jsonObjectRequest.put("searchcontent", id);
            jsonObjectRequest.put("quality", "320");


            JsonObjectRequest movieDetailsRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videoquality/fetchvideoquality",
                    jsonObjectRequest, response -> {

                if (!response.equals("No Data Avaliable")) {
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    List<PlayMovieItemIist> playMovieItemList = new ArrayList<>();

                    RecyclerView playMovieItemRecylerview = findViewById(R.id.Play_movie_item_Recylerview);
                    JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                    for (JsonElement r : jsonArray) {
                        JsonObject rootObject = r.getAsJsonObject();
                        //JsonObject rootObject = (JsonObject) jsonArray.get(0);

                        id1 = rootObject.get("videoqualityid").getAsInt();
                        movieName = rootObject.getAsJsonObject("videoContent").get("title").getAsString();
                        size = "";
                        quality = rootObject.get("quality").getAsString();
                        movieId = id;
                        url = rootObject.get("signedurl").getAsString();
                        String type = "mp4"; //rootObject.getAsJsonObject("videoContent").getAsJsonObject("contentType").get("contenttypename").getAsString();
                        String status = rootObject.get("status").getAsString();
                        skipAvailable = 1;//rootObject.get("skip_available").getAsInt();
                        introStart = "";//rootObject.get("intro_start").getAsString();
                        introEnd = "";//rootObject.get("intro_end").getAsString();
                        link_type = 0;//rootObject.get("link_type").getAsInt();
                        drm_uuid = "";//rootObject.get("drm_uuid").isJsonNull() ? "" : rootObject.get("drm_uuid").getAsString();
                        drm_license_uri = "";//rootObject.get("drm_license_uri").isJsonNull() ? "" : rootObject.get("drm_license_uri").getAsString();

                        //if (status == 1) {
                        playMovieItemList.add(new PlayMovieItemIist(id1, movieName, size, quality, movieId, url, type, skipAvailable, introStart, introEnd, link_type, drm_uuid, drm_license_uri));
                        // }


                        PlayMovieItemListAdepter myadepter = new PlayMovieItemListAdepter(id, context, playMovieItemList, playPremium);
                        playMovieItemRecylerview.setLayoutManager(new GridLayoutManager(context, 1));
                        playMovieItemRecylerview.setAdapter(myadepter);

                    }
                playMovieTab(true);
            } else {
                Snackbar snackbar = Snackbar.make(rootView, "No Stream Avaliable!", Snackbar.LENGTH_SHORT);
                snackbar.setAction("Close", v -> snackbar.dismiss());
                snackbar.show();
            }
            loadingDialog.animate(false);
        }, error -> {
            loadingDialog.animate(false);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                // params.put("x-api-key", AppConfig.apiKey);
                return params;
            }

        };
            movieDetailsRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(movieDetailsRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    void loadMovieDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonObjectRequest = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObjectRequest.put("email", "admin@gmail.com");
            jsonObjectRequest.put("usermode", "admin");
            jsonObjectRequest.put("caller", "mobile");
            jsonObjectRequest.put("searchtype", "videocontentid");
            jsonObjectRequest.put("searchcontent", id);


            JsonObjectRequest movieDetailsRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObjectRequest, response -> {

                if (!response.equals("No Data Avaliable")) {
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");

                    JsonObject jsonObject = (JsonObject) jsonArray.get(0);

                    trailerUrl = jsonObject.get("trailerurl").getAsString();
                    trailerextention = jsonObject.get("trailerextention").getAsString();
                    contentId = jsonObject.get("videocontentid").getAsInt();
                   // TMDB_ID = jsonObject.get("TMDB_ID").getAsInt();
                    loadAdditionalDetails(jsonObject);

                    name = jsonObject.get("title").getAsString();
                    videoTileIdController.setText(name);
                    if (!jsonObject.get("releasedate").getAsString().equals("")) {
                        releaseDate = jsonObject.get("releasedate").getAsString();
                    }
                    runtime = jsonObject.get("duration").getAsString();
                    genres = jsonObject.getAsJsonObject("contentCatagory").get("catagoryname").getAsString();
                    poster = jsonObject.get("posterimageurl").getAsString();
                    banner = jsonObject.get("thumbnailimageurl").getAsString();
                    //downloadable = jsonObject.get("downloadable").getAsInt();
                    type = 0;
                   // status = jsonObject.get("status").getAsInt();
                    description = jsonObject.get("description").getAsString();

                    TextView titleTextView = findViewById(R.id.Title_TextView);
                    titleTextView.setText(name);

                    TextView releaseDateTextView = findViewById(R.id.ReleaseDate_TextView);
                    if(releaseDate!=null) {
                        releaseDateTextView.setText(releaseDate);
                    } else {
                        releaseDateTextView.setVisibility(View.GONE);
                    }


                    TextView runtimeTextView = findViewById(R.id.Runtime_TextView);
                    runtimeTextView.setText(runtime);

                    TextView genreTextView = findViewById(R.id.Genre_TextView);
                    genreTextView.setText(genres);

                    movieDetailsBanner = findViewById(R.id.Movie_Details_Banner);
                    ImageView movieDetailsPoster = findViewById(R.id.Movie_Details_Poster);
                    if(AppConfig.safeMode) {
                        Glide.with(MovieDetails.this)
                                .load(R.drawable.poster_placeholder)
                                .override(80, 80)
                                .placeholder(R.drawable.poster_placeholder)
                                .into(movieDetailsBanner);

                        Glide.with(MovieDetails.this)
                                .load(R.drawable.thumbnail_placeholder)
                                .placeholder(R.drawable.thumbnail_placeholder)
                                .into(movieDetailsPoster);
                    } else {
                        Glide.with(MovieDetails.this)
                                .load(banner)
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 3)))
                                .placeholder(R.drawable.poster_placeholder)
                                .into(movieDetailsBanner);

                        Glide.with(MovieDetails.this)
                                .load(poster)
                                .placeholder(R.drawable.thumbnail_placeholder)
                                .into(movieDetailsPoster);
                    }

                    View premiumTag = findViewById(R.id.Premium_Tag);
                    if(AppConfig.all_movies_type == 0) {
                        if(type== 1) {
                            premiumTag.setVisibility(View.VISIBLE);
                        } else {
                            premiumTag.setVisibility(View.GONE);
                        }
                    } else if(AppConfig.all_movies_type == 1) {
                        premiumTag.setVisibility(View.GONE);
                    } else if(AppConfig.all_movies_type == 2) {
                        premiumTag.setVisibility(View.VISIBLE);
                    }

                    TextView descriptionTextView = findViewById(R.id.Description_TextView);
                    descriptionTextView.setText(description);

                    if(trailerUrl.equals("")) {
                        trailerIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.trailer_blocked_icon));
                    } else {
                        trailerIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.trailer_icon));
                    }

                    if(downloadable == 0) {
                        downloadIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.download_blocked_icon));
                    } else if(downloadable == 1) {
                        downloadIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.download_icon));
                    }


                    searchFavourite();

                    getRelated(genres);


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
            queue.add(movieDetailsRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    void getRelated(String genres) {
        LinearLayoutCompat reletedContentLayout = findViewById(R.id.reletedContentLayout);
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObject.put("email", "admin@gmail.com");
            jsonObject.put("usermode", "admin");
            jsonObject.put("caller", "mobile");
            jsonObject.put("searchtype", "categorytype");
            jsonObject.put("searchcontent", genres);


            JsonObjectRequest trendingRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {

                if(!response.equals("No Data Avaliable")) {
                    reletedContentLayout.setVisibility(View.VISIBLE);
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                    List<MovieList> movieList = new ArrayList<>();
                    for (JsonElement r : jsonArray) {
                        JsonObject rootObject = r.getAsJsonObject();

                        int m_id = rootObject.get("videocontentid").getAsInt();
                        String name = rootObject.get("title").getAsString();
                        String banner = rootObject.get("posterimageurl").getAsString();

                        String year = "";
                        if(!rootObject.get("releasedate").getAsString().equals("")) {
                            year = HelperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                        }

                        int type = 0;
                      //String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                        //int type = rootObject.get("type").getAsInt();
                       // int status = rootObject.get("status").getAsInt();

                        if (id != m_id) {
                            movieList.add(new MovieList(m_id, type, name, year, banner));
                        }

                        Collections.shuffle(movieList);

                        RecyclerView reletedContentRecycleview = findViewById(R.id.reletedContentRecycleview);
                        ReletedMovieListAdepter myadepter = new ReletedMovieListAdepter(context, movieList);
                        reletedContentRecycleview.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false));
                        reletedContentRecycleview.setAdapter(myadepter);
                    }
                } else {
                    reletedContentLayout.setVisibility(View.GONE);
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

    void setFavourite() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"favourite/SET/"+ tempUserID +"/Movie/"+contentId, response -> {
            if(response.equals("New favourite created successfully")) {
               isFavourite = true;
               favouriteIcon.setImageDrawable(ContextCompat.getDrawable(MovieDetails.this, R.drawable.red_heart_favorite));
            }

        }, error -> {
            // Do nothing
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    void searchFavourite() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"favourite/SEARCH/"+ tempUserID +"/Movie/"+contentId, response -> {
            if(response.equals("Record Found")) {
                isFavourite = true;
                favouriteIcon.setImageDrawable(ContextCompat.getDrawable(MovieDetails.this, R.drawable.red_heart_favorite));
            }

        }, error -> {
            // Do nothing
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    void removeFavourite() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"favourite/REMOVE/"+ tempUserID +"/Movie/"+contentId, response -> {
            if(response.equals("Favourite successfully Removed")) {
                isFavourite = false;
                favouriteIcon.setImageDrawable(ContextCompat.getDrawable(MovieDetails.this, R.drawable.heart_favorite));
            }

        }, error -> {
            // Do nothing
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("x-api-key", AppConfig.apiKey);
                return params;
            }
        };
        queue.add(sr);
    }

    private void loadUserSubscriptionDetails() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);
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
        //adType = jsonObject.get("ad_type").getAsInt();

//        if(jsonObject.get("movie_comments").getAsInt() == 1) {
//            initComment();
//        }

        int onScreenEffect = 0; //jsonObject.get("onscreen_effect").getAsInt();
        SnowfallView SnowfallView = findViewById(R.id.SnowfallView);
        switch (onScreenEffect) {
            case 0:
                SnowfallView.setVisibility(View.GONE);
                break;
            case 1:
                SnowfallView.setVisibility(View.VISIBLE);
                break;
            default:
                SnowfallView.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBackPressed() {
        View playMovieTab = findViewById(R.id.Play_Movie_Tab);
        ConstraintLayout customIntertial_layout = findViewById(R.id.customIntertial_layout);
        if (activeMovie) {
            if (player != null) {
                player.setPlayWhenReady(false);
                player.stop();
            }
            showDescriptionLayout();
            activeMovie = false;
        }else if (isFullscreen) {
            toggleFullscreen();
        }else {
            releasePlayer();
            super.onBackPressed();
        }
        if(playMovieTab.getVisibility() == View.VISIBLE) {
            playMovieTab(false);
        } else if(customIntertial_layout.getVisibility() == View.VISIBLE) {
            customIntertial_layout.setVisibility(View.GONE);
        } else {
            finish();
        }
//        if (activeMovie) {
//            if (player != null) {
//                player.setPlayWhenReady(false);
//                player.stop();
//            }
//            showDescriptionLayout();
//            activeMovie = false;
//        }else if (isFullscreen) {
//            toggleFullscreen();
//        }else {
//            releasePlayer();
//            super.onBackPressed();
//        }
    }
    public void releasePlayer() {
        if (player != null) {
            player.setPlayWhenReady(true);
            player.stop();
            player.release();
            player = null;
            simpleExoPlayerView.setPlayer(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        player1.resume();
        FullSrceen.hideSystemUI(getWindow());
        if (player != null) {
            if (trailertype != null) {
                    player.setPlayWhenReady(true);
                }
        }

       // super.onResume();

       // playMovieTab(false);

        if(!AppConfig.allowVPN) {
            //check vpn connection
            helperUtils = new HelperUtils(MovieDetails.this);
            vpnStatus = helperUtils.isVpnConnectionAvailable();
            if (vpnStatus) {
                helperUtils.showWarningDialog(MovieDetails.this, "VPN!", "You are Not Allowed To Use VPN Here!", R.raw.network_activity_icon);
            }
        }
    }
}
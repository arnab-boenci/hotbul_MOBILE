package com.dooo.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.AllMoviesActivity;
import com.dooo.android.AllWebSeriesActivity;
import com.dooo.android.AppConfig;
import com.dooo.android.ContentInCategory;
import com.dooo.android.R;
import com.dooo.android.adepter.ImageSliderAdepter;
import com.dooo.android.adepter.PopularSearchListAdepter;
import com.dooo.android.adepter.SearchListAdepter;
import com.dooo.android.list.ImageSliderItem;
import com.dooo.android.list.SearchList;
import com.dooo.android.utils.HelperUtils;
import com.dooo.android.utils.TinyDB;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    private Context context;
    private static final int nRESULT_SPEECH = 0147;

    AutoCompleteTextView searchContentEditText;
    ImageView search_mic_icon;
    View bigSearchLottieAnimation;
    RecyclerView searchLayoutRecyclerView;

    RecyclerView popular_search_Layout_RecyclerView;
    LinearLayout top_genres_Layout;
    LinearLayout popularSearchLayout;
    SwitchMaterial includePremiumSwitch;
    int onlyPremium = 1;
    TinyDB tinyDB;

    TextView latest,action, comedy, horror, detective,drama,romance,adult,kids,music,old,upcoming;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View layoutInflater = inflater.inflate(R.layout.fragment_search, container, false);
        bindViews(layoutInflater);
        tinyDB = new TinyDB(context);

        search_mic_icon.setOnClickListener(view ->{
            Intent mic_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mic_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            startActivityForResult(mic_intent, nRESULT_SPEECH);
        });

        searchContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(String.valueOf(searchContentEditText.getText()).equals("")) {
                    //bigSearchLottieAnimation.setVisibility(View.VISIBLE);
                    searchLayoutRecyclerView.setVisibility(View.GONE);
                    popular_search_Layout_RecyclerView.setVisibility(View.VISIBLE);
                    popularSearchLayout.setVisibility(View.VISIBLE);
                    top_genres_Layout.setVisibility(View.VISIBLE);
                } else  {
                    bigSearchLottieAnimation.setVisibility(View.GONE);
                    popular_search_Layout_RecyclerView.setVisibility(View.GONE);
                    top_genres_Layout.setVisibility(View.GONE);
                    popularSearchLayout.setVisibility(View.GONE);
                    searchLayoutRecyclerView.setVisibility(View.VISIBLE);

                    searchContent(String.valueOf(searchContentEditText.getText()));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });

        includePremiumSwitch.setChecked(tinyDB.getBoolean("onlyPremium"));
        if(tinyDB.getBoolean("onlyPremium")) {
            onlyPremium = 1;
        } else {
            onlyPremium = 0;
        }
        includePremiumSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tinyDB.putBoolean("onlyPremium", isChecked);
                if(isChecked) {
                    onlyPremium = 1;
                } else {
                    onlyPremium = 0;
                }
            }
        });

        searchContentEditText.setOnItemClickListener((parent, view, position, id) -> {
            bigSearchLottieAnimation.setVisibility(View.GONE);
            popular_search_Layout_RecyclerView.setVisibility(View.GONE);
            top_genres_Layout.setVisibility(View.GONE);
            popularSearchLayout.setVisibility(View.GONE);
            searchLayoutRecyclerView.setVisibility(View.VISIBLE);
            searchContent(searchContentEditText.getAdapter().getItem(position).toString());
        });
        poplarSearcContent();

        setColorTheme(Color.parseColor(AppConfig.primeryThemeColor), layoutInflater);
        latest = layoutInflater.findViewById(R.id.latest);
        action = layoutInflater.findViewById(R.id.action);
        comedy = layoutInflater.findViewById(R.id.comedy);
        horror = layoutInflater.findViewById(R.id.horror);
        detective = layoutInflater.findViewById(R.id.detective);
        drama = layoutInflater.findViewById(R.id.drama);
        romance = layoutInflater.findViewById(R.id.romance);
        adult = layoutInflater.findViewById(R.id.adult);
        kids = layoutInflater.findViewById(R.id.kids);
        music = layoutInflater.findViewById(R.id.music);
        old = layoutInflater.findViewById(R.id.old);
        upcoming = layoutInflater.findViewById(R.id.upcoming);

        latest.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","recentlyadded");
            intent.putExtra("cName","Latest");
            intent.putExtra("cType","Movie");
            startActivity(intent);
        });

        action.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Action");
            intent.putExtra("cType","Action");
            startActivity(intent);
        });
        comedy.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Comedy");
            intent.putExtra("cType","Comedy");
            startActivity(intent);
        });

        horror.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Horror");
            intent.putExtra("cType","Horror");
            startActivity(intent);
        });
        detective.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Detective");
            intent.putExtra("cType","Detective");
            startActivity(intent);
        });

        drama.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Drama");
            intent.putExtra("cType","Drama");
            startActivity(intent);
        });
        romance.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Romance");
            intent.putExtra("cType","Romance");
            startActivity(intent);
        });

        adult.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","contenttype");
            intent.putExtra("cName","Adult");
            intent.putExtra("cType","18+");
            startActivity(intent);
        });
        kids.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","contenttype");
            intent.putExtra("cName","Kids");
            intent.putExtra("cType","Kids");
            startActivity(intent);
        });

        music.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","contenttype");
            intent.putExtra("cName","Music");
            intent.putExtra("cType","Music");
            startActivity(intent);
        });
        old.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","categorytype");
            intent.putExtra("cName","Old");
            intent.putExtra("cType","Old is Gold");
            startActivity(intent);
        });

        upcoming.setOnClickListener(view -> {
            Intent intent = new Intent(context, ContentInCategory.class);
            intent.putExtra("cSearchType","contenttype");
            intent.putExtra("cName","Upcoming");
            intent.putExtra("cType","Upcoming");
            startActivity(intent);
        });


        return layoutInflater;
    }

    private void bindViews(View layoutInflater) {
        searchContentEditText = layoutInflater.findViewById(R.id.Search_content_editText);
        search_mic_icon = layoutInflater.findViewById(R.id.search_mic_icon);
        top_genres_Layout = layoutInflater.findViewById(R.id.top_genres_Layout);
        popularSearchLayout = layoutInflater.findViewById(R.id.popularSearchLayout);
        bigSearchLottieAnimation = layoutInflater.findViewById(R.id.big_search_Lottie_animation);
        searchLayoutRecyclerView = layoutInflater.findViewById(R.id.Search_Layout_RecyclerView);
        popular_search_Layout_RecyclerView = layoutInflater.findViewById(R.id.popular_search_Layout_RecyclerView);
        includePremiumSwitch = layoutInflater.findViewById(R.id.includePremiumSwitch);
        GridLayoutManager popularSearchGridLayout = new GridLayoutManager(context, 2);
        popular_search_Layout_RecyclerView.setLayoutManager(popularSearchGridLayout);
        //searchLayoutRecyclerView.setLayoutManager(popularSearchGridLayout);
    }

    void setColorTheme(int color, View layoutInflater) {
        TextView searchText = layoutInflater.findViewById(R.id.searchText);
        searchText.setTextColor(color);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case nRESULT_SPEECH:
                if (null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String textCapturedFromVoice=text.get(0);
                    searchContentEditText.setText(textCapturedFromVoice);
                }
                break;
        }
    }

    void searchContent(String text) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObject.put("email", "admin@gmail.com");
            jsonObject.put("password", "123456");
            jsonObject.put("usermode", "admin");
            jsonObject.put("caller", "webadmin");
            jsonObject.put("searchtype", "charectersearch");
            jsonObject.put("searchcontent", text);


            JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {

                if(!response.equals("No Data Avaliable")) {
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")) {
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<SearchList> searchList = new ArrayList<>();
                        int i = 0;
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            String year = "";
                            if(!rootObject.get("releasedate").getAsString().equals("")) {
                                year = HelperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }
                            String content_type = "Movies"; //rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                            searchList.add(new SearchList(id, type, name, year, banner, content_type));
                        }

                        SearchListAdepter myadepter = new SearchListAdepter(context, searchList);
                        searchLayoutRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        searchLayoutRecyclerView.setAdapter(myadepter);
                    }else{
                       // bigSearchLottieAnimation.setVisibility(View.VISIBLE);
                        searchLayoutRecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    //bigSearchLottieAnimation.setVisibility(View.VISIBLE);
                    searchLayoutRecyclerView.setVisibility(View.GONE);
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
            queue.add(searchRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        StringRequest sr = new StringRequest(Request.Method.GET, AppConfig.url +"searchContent/"+text+"/"+onlyPremium, response -> {
//            if(!response.equals("No Data Avaliable")) {
//                JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
//                List<SearchList> searchList = new ArrayList<>();
//                for (JsonElement r : jsonArray) {
//                    JsonObject rootObject = r.getAsJsonObject();
//                    int id = rootObject.get("id").getAsInt();
//                    String name = rootObject.get("name").getAsString();
//
//                    String year = "";
//                    if(!rootObject.get("release_date").getAsString().equals("")) {
//                        year = HelperUtils.getYearFromDate(rootObject.get("release_date").getAsString());
//                    }
//
//                    String poster = rootObject.get("poster").getAsString();
//                    int type = rootObject.get("type").getAsInt();
//                    int status = rootObject.get("status").getAsInt();
//                    int contentType = rootObject.get("content_type").getAsInt();
//
//                    if (status == 1) {
//                        searchList.add(new SearchList(id, type, name, year, poster, contentType));
//                    }
//                }
//
//                SearchListAdepter myadepter = new SearchListAdepter(context, searchList);
//                searchLayoutRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
//                searchLayoutRecyclerView.setAdapter(myadepter);
//
//            } else {
//                bigSearchLottieAnimation.setVisibility(View.VISIBLE);
//                searchLayoutRecyclerView.setVisibility(View.GONE);
//            }
//        }, error -> {
//            // Do nothing because There is No Error if error It will return 0
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                //params.put("x-api-key", AppConfig.apiKey);
//                return params;
//            }
//        };
       // queue.add(sr);
    }

    void poplarSearcContent() {
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObject.put("email", "admin@gmail.com");
            jsonObject.put("password", "123456");
            jsonObject.put("usermode", "admin");
            jsonObject.put("caller", "webadmin");
            jsonObject.put("searchtype", "viewcount");
            jsonObject.put("searchcontent", "Movie");


            JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videocontent/fetchvideocontent",
                    jsonObject, response -> {

                if(!response.equals("No Data Avaliable")) {
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    if(jsonObjectResponse.get("status").getAsString().equalsIgnoreCase("Success")) {
                        JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");
                        List<SearchList> searchList = new ArrayList<>();
                        int i = 0;
                        for (JsonElement r : jsonArray) {
                            JsonObject rootObject = r.getAsJsonObject();
                            int id = rootObject.get("videocontentid").getAsInt();
                            String name = rootObject.get("title").getAsString();
                            String banner = rootObject.get("posterimageurl").getAsString();
                            int type = 0;
                            String year = "";
                            if(!rootObject.get("releasedate").getAsString().equals("")) {
                                year = HelperUtils.getYearFromDate(rootObject.get("releasedate").getAsString());
                            }
                            String content_type = rootObject.getAsJsonObject("contentType").get("contenttypename").getAsString();
                            searchList.add(new SearchList(id, type, name, year, banner, content_type));
                        }
                        popular_search_Layout_RecyclerView.setVisibility(View.VISIBLE);
                        popularSearchLayout.setVisibility(View.VISIBLE);
                        top_genres_Layout.setVisibility(View.VISIBLE);
                        PopularSearchListAdepter myadepter = new PopularSearchListAdepter(context, searchList);
                        popular_search_Layout_RecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        popular_search_Layout_RecyclerView.setAdapter(myadepter);
                    }else{
                        // bigSearchLottieAnimation.setVisibility(View.VISIBLE);
                        popular_search_Layout_RecyclerView.setVisibility(View.GONE);
                    }

                } else {
                    //bigSearchLottieAnimation.setVisibility(View.VISIBLE);
                    searchLayoutRecyclerView.setVisibility(View.GONE);
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
            queue.add(searchRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
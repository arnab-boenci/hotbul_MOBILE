package com.dooo.android;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dooo.android.list.PlayMovieItemIist;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.material.snackbar.Snackbar;
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

public class HLSTEST extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hls_test);

        playerView = findViewById(R.id.player_view);

        // Initialize ExoPlayer
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        // Add an EventLogger to get detailed logs
        player.addAnalyticsListener(new EventLogger());

        // Play the HLS stream
        String hlsUrl = loadStreamLinks(153);

    }

    String loadStreamLinks(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String[] strurl = {null};
        JSONObject jsonObjectRequest = new JSONObject();
        try {
            // Populate JSON object with provided data
            jsonObjectRequest.put("email", "admin@gmail.com");
            jsonObjectRequest.put("usermode", "admin");
            jsonObjectRequest.put("caller", "mobile");
            jsonObjectRequest.put("searchtype", "videocontentid");
            jsonObjectRequest.put("searchcontent", id);
            jsonObjectRequest.put("quality", "480");


            JsonObjectRequest movieDetailsRequest = new JsonObjectRequest(Request.Method.POST, AppConfig.baseurl +"/videoquality/fetchvideoquality",
                    jsonObjectRequest, response -> {

                if (!response.equals("No Data Avaliable")) {
                    JsonObject jsonObjectResponse = new Gson().fromJson(response.toString(), JsonObject.class);
                    List<PlayMovieItemIist> playMovieItemList = new ArrayList<>();

                    RecyclerView playMovieItemRecylerview = findViewById(R.id.Play_movie_item_Recylerview);
                    JsonArray jsonArray = jsonObjectResponse.getAsJsonArray("resultList");

                    JsonObject rootObject = jsonArray.get(0).getAsJsonObject();
                    String url = rootObject.get("signedurl").getAsString().toString();
                    playHlsStream(url);
                } else {
                }
            }, error -> {
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
        return strurl[0];

    }

    private void playHlsStream(String url) {
        Uri uri = Uri.parse(url);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    Log.d(TAG, "Player is ready");
                } else if (state == Player.STATE_BUFFERING) {
                    Log.d(TAG, "Player is buffering");
                } else if (state == Player.STATE_ENDED) {
                    Log.d(TAG, "Playback ended");
                } else if (state == Player.STATE_IDLE) {
                    Log.d(TAG, "Player is idle");
                }
            }


            public void onPlayerError(com.google.android.exoplayer2.ExoPlaybackException error) {
                Log.e(TAG, "Player error: " + error.getMessage());
                runOnUiThread(() -> Toast.makeText(HLSTEST.this, "Player error: " + error.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
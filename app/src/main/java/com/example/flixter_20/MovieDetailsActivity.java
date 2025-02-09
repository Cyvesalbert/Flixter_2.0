package com.example.flixter_20;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter_20.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {
    //private static final String YOUTUBE_API_KEY = "AIzaSyBdd08opA5eVPotBfKlj1eaS4mlxSHTbgA";
    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    String movieId;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    WebView webView;
    // Enable JavaScript (required for YouTube)
    WebSettings webSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        webView = findViewById(R.id.webView);

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Retrieve Parcelable object
        Movie movie = getIntent().getParcelableExtra("movie");

        // Ensure movie is not null before accessing its properties
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            ratingBar.setRating((float) movie.getRating()); // Assuming getRating() returns a double
        } else {
            Log.e("MovieDetailsActivity", "Movie object is null!");
        }

        Log.d("MovieDetailsActivity", movie.getMovieId());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }

                    // YouTube video ID
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("youtubeKey", youtubeKey);

                    // Load the YouTube embed URL in an iframe
                    String iframe = "<html><body><iframe width=\"100%\" height=\"100%\" " +
                            "src=\"https://www.youtube.com/embed/" + youtubeKey + "\" " +
                            "frameborder=\"0\" allowfullscreen></iframe></body></html>";

                    webView.loadData(iframe, "text/html", "utf-8");
                } catch (JSONException e) {
                    Log.e("youtubeKey", "Failed to parse JSON", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("youtubeKey", "Failed to get data from videos endpoint", throwable);
            }
        });




    }
}
package com.example.flixter_20;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flixter_20.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);

        // Retrieve Parcelable object
        Movie movie = getIntent().getParcelableExtra("movie");

        // Ensure movie is not null before accessing its properties
        if (movie != null) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //ratingBar.setRating((float) movie.getRating()); // Assuming getRating() returns a double
        } else {
            Log.e("MovieDetailsActivity", "Movie object is null!");
        }
    }
}
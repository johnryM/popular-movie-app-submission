package com.mangonon.johnry.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangonon.johnry.popularmovieapp.app.model.Movie;
import com.mangonon.johnry.popularmovieapp.utils.Helper;
import com.mangonon.johnry.popularmovieapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private Movie mMovie;

    private ImageView mImage;
    private TextView mTitle;
    private TextView mSynopsis;
    private TextView mRating;
    private TextView mRelease;

    public static Intent newInstance(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (mMovie != null) {
            mImage = (ImageView) findViewById(R.id.movie_image_view);
            mTitle = (TextView) findViewById(R.id.movie_title_view);
            mSynopsis = (TextView) findViewById(R.id.movie_synopsis_view);
            mRating = (TextView) findViewById(R.id.movie_rating_view);
            mRelease = (TextView) findViewById(R.id.movie_rdate_view);

            String moviePath = mMovie.getmImageUrl();
            String url = NetworkUtils.buildMovieImageUrl("w500", moviePath).toString();

            Picasso.with(this)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(mImage);

            mTitle.setText(mMovie.getmTitle());
            mSynopsis.setText(mMovie.getmSynopsis());
            mRating.setText(String.valueOf(mMovie.getmRating()));

            try {
                mRelease.setText(Helper.getYear(mMovie.getmReleaseDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}

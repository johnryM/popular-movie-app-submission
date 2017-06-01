package com.mangonon.johnry.popularmovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private final int SPAN_COUNT = 2;

    private RecyclerView mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieList = (RecyclerView) findViewById(R.id.movie_list);
        mMovieList.setHasFixedSize(true);
        mMovieList.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mMovieList.setAdapter(new MovieAdapter());
    }
}

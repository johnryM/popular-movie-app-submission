package com.mangonon.johnry.popularmovieapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by skeeno on 29/05/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int counter = 10;
    ImageView mMovieImageView;
    TextView mTitleTextView;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_grid_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int position) {
        Log.d("Position", Integer.toString(position));
        movieViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return counter;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {


        public MovieViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);

        }

        public void bind(int position) {
            mTitleTextView.setText(String.valueOf(position));
            Picasso.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                    .into(mMovieImageView);
        }

    }

}

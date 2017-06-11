package com.mangonon.johnry.popularmovieapp;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mangonon.johnry.popularmovieapp.app.model.Movie;
import com.mangonon.johnry.popularmovieapp.utils.ConnectionTask;
import com.mangonon.johnry.popularmovieapp.utils.Helper;
import com.mangonon.johnry.popularmovieapp.utils.JsonUtils;
import com.mangonon.johnry.popularmovieapp.utils.NetworkUtils;
import com.mangonon.johnry.popularmovieapp.ui.MovieAdapter;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.AdapterClickListener,
        SortDialogFragment.SortDialogListener, ConnectionTask.ConnectionTaskCallback {

    private final int SPAN_COUNT_PORTRAIT = 2;
    private final int SPAN_COUNT_LANDSCAPE = 3;

    private ArrayList<Movie> mMovieDatalist = new ArrayList<>();

    @BindView(R.id.movie_list) RecyclerView mMovieList;
    @BindView(R.id.error_text_view) TextView mErrorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        int SPAN_COUNT = Helper.isLandscape(this) ? SPAN_COUNT_LANDSCAPE : SPAN_COUNT_PORTRAIT;
        mMovieList.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mMovieList.setHasFixedSize(true);

        if (NetworkUtils.isOnline(this)) {
            new ConnectionTask(this).execute(NetworkUtils.buildMovieDataUrl(NetworkUtils.SortType.POPULAR));
        } else {
            showErrorView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort) {
            showSortDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleOnClick(int position) {
        Movie movie = mMovieDatalist.get(position);
        startActivity(MovieDetailsActivity.newInstance(this, movie));
    }

    @Override
    public void onSortSelected(NetworkUtils.SortType sortType) {
        new ConnectionTask(this).execute(NetworkUtils.buildMovieDataUrl(sortType));
        MovieAdapter movieAdapter = (MovieAdapter) mMovieList.getAdapter();

        if (movieAdapter == null) {
            mMovieList.setAdapter(new MovieAdapter(mMovieDatalist, this));
        } else {
            movieAdapter.refresh(this, mMovieDatalist);
        }
        showMovies();
    }

    private void showSortDialog() {
        if (!NetworkUtils.isOnline(this)) {
            Toast.makeText(this, "No connection",Toast.LENGTH_SHORT).show();
            return;
        }

        FragmentManager fm  = getSupportFragmentManager();
        SortDialogFragment sortDialog = new SortDialogFragment();
        sortDialog.show(fm, "sort_fragment");
    }

    private void showErrorView() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mMovieList.setVisibility(View.INVISIBLE);
    }

    private void showMovies() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMovieList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskDone(String output) {
        try {
            mMovieDatalist = JsonUtils.getMovieItems(MainActivity.this, output);
            mMovieList.setAdapter(new MovieAdapter(mMovieDatalist, MainActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error getting Movie Data", Toast.LENGTH_LONG).show();
        }
    }
}

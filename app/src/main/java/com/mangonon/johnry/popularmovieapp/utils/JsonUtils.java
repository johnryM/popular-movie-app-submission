package com.mangonon.johnry.popularmovieapp.utils;

import android.content.Context;

import com.mangonon.johnry.popularmovieapp.app.model.Movie;
import com.mangonon.johnry.popularmovieapp.app.model.MovieBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skeeno on 04/06/2017.
 */

public class JsonUtils {

    private static String ARRAY_ELEMENT = "results";
    private static String IMAGE_PATH = "poster_path";
    private static String SYNOPSIS = "overview";
    private static String RATING = "vote_average";
    private static String RELEASE_DATE = "release_date";
    private static String TITLE = "original_title";

    public static ArrayList<Movie> getMovieItems(Context context, String jsonString) throws JSONException {

        if (jsonString == null) {
            return null;
        }

        ArrayList<Movie> movieList = new ArrayList<>();

        JSONObject movieJsonObject = new JSONObject(jsonString);

        JSONArray movieJsonArray = movieJsonObject.getJSONArray(ARRAY_ELEMENT);

        //TODO flesh out handling of error responses
        if (movieJsonArray == null) {
            return null;
        }

        for (int i = 0; i < movieJsonArray.length(); i++) {

            JSONObject movieDetailsObject = movieJsonArray.getJSONObject(i);

            Movie movie = new MovieBuilder()
                    .setImageUrl(formatImagePath(movieDetailsObject.getString(IMAGE_PATH)))
                    .setSynopsis(movieDetailsObject.getString(SYNOPSIS))
                    .setRating(Float.parseFloat(movieDetailsObject.getString(RATING)))
                    .setReleaseDate(movieDetailsObject.getString(RELEASE_DATE))
                    .setTitle(movieDetailsObject.getString(TITLE))
                    .createMovie();

            movieList.add(movie);
        }

        return movieList;
    }

    private static String formatImagePath(String oldPath) {
        return oldPath.substring(1);
    }

}

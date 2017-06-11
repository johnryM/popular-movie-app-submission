package com.mangonon.johnry.popularmovieapp.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class ConnectionTask extends AsyncTask<URL, Void, String > {

    ConnectionTaskCallback mCallback;

    public ConnectionTask(ConnectionTaskCallback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String output = null;
        try {
            output =  NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(String output) {
        super.onPostExecute(output);

        mCallback.onTaskDone(output);
    }

    public interface ConnectionTaskCallback {
        void onTaskDone(String output);
    }
}

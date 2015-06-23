package com.udacity.gradle.builtitbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abhilash.mylibrary.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.krypto.backend.jokeApi.JokeApi;
import com.krypto.backend.jokeApi.model.Joke;

import java.io.IOException;

/**
 * Downloads the joke from the server and starts new activity
 */
public class EndPointsAysncTask extends AsyncTask<Void, Void, String> {
    private JokeApi myApiService = null;
    private Context mContext;
    ProgressBar mProgressBar;


    public EndPointsAysncTask(Context c, ProgressBar progressBar) {
        mContext = c;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://endpoint-backend.appspot.com/_ah/api/").setApplicationName("backend");
            myApiService = builder.build();
        }

        try {
            return myApiService.insertJoke(new Joke()).execute().getTheJoke();

        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(mContext, JokeActivity.class);
        intent.putExtra("extra", s);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}



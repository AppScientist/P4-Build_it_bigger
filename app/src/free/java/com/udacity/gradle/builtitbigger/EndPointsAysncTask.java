package com.udacity.gradle.builtitbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.abhilash.mylibrary.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.krypto.backend.jokeApi.JokeApi;
import com.krypto.backend.jokeApi.model.Joke;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;

/**
 * Downloads the joke from the server and starts new activity
 */
public class EndPointsAysncTask extends AsyncTask<Void, Void, String> {
    private JokeApi myApiService = null;
    private Context mContext;
    private InterstitialAd mInterstitial;
    private ProgressBar mProgressBar;


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
    protected void onPostExecute(final String result) {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
        mInterstitial = new InterstitialAd(mContext);
        mInterstitial.setAdUnitId(mContext.getString(R.string.interstitial_ad_unit_id));
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitial.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent intent = new Intent(mContext, JokeActivity.class);
                intent.putExtra("extra", result);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        AdRequest ar = new AdRequest.Builder().build();
        mInterstitial.loadAd(ar);

    }
}



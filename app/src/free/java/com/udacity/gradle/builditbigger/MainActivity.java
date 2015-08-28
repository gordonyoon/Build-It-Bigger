package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gordonyoon.jokedisplayer.JokeDisplayer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.Bind;
import butterknife.ButterKnife;
import interfaces.EndpointResultListener;


public class MainActivity extends ActionBarActivity implements EndpointResultListener {
    @Bind(R.id.progress_bar)
    ProgressBar mSpinner;
    InterstitialAd mInterstitialAd;
    private boolean receivingJoke = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJokeDisplayer();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void showJokeDisplayer() {
        new AsyncJokeDisplayer(MainActivity.this).showJoke();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSpinner.setVisibility(View.GONE);
        receivingJoke = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        mSpinner.setVisibility(View.VISIBLE);
        if (!receivingJoke) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                showJokeDisplayer();
            }
            receivingJoke = true;
        }
    }

    @Override
    public void onJokeReceived(String joke) {
        JokeDisplayer.start(this, joke);
    }
}

package com.udacity.gradle.builditbigger;


import android.os.AsyncTask;

import com.example.gordonyoon.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import interfaces.EndpointResultListener;

public class AsyncJokeDisplayer {
    private EndpointResultListener mListener;

    public AsyncJokeDisplayer(EndpointResultListener listener) {
        this.mListener = listener;
    }

    public void showJoke() {
        new EndpointsAsyncTask().execute();
    }

    class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private final static String IP_GENYMOTION = "http://10.0.3.2:8080/_ah/api/";
        private final static String IP_ANDROID_EMULATOR = "http://10.0.2.2:8080/_ah/api/";

        @Override
        protected String doInBackground(Void... params) {
            // for deploying locally
//            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//                    .setRootUrl(IP_GENYMOTION)
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

            // for deploying on GAE
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://build-it-bigger-1051.appspot.com/_ah/api/");

            MyApi myApiService = builder.build();

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String joke) {
            if (joke != null && !joke.isEmpty()) {
                mListener.onJokeReceived(joke);
            } else {
                mListener.onJokeReceived("Something went wrong!");
            }
        }
    }
}

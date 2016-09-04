package com.foxbard.pydev.subactivity;

import android.content.Context;

import android.os.AsyncTask;

import android.util.Log;

import com.example.JokeLibrary;

import com.example.pydev.myapplication.backend.jokeApi.JokeApi;
import com.example.pydev.myapplication.backend.jokeApi.model.Joke;
import com.foxbard.pydev.subactivity.messages.SendJokeEvent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Random;


/**
 * Created by PY-DEV on 8/26/2016.
 */
class EndpointsAsyncTask extends AsyncTask<JokeLibrary, Void, Joke> {

    private static JokeApi jokesApi = null;
    private Context context;
    String LOG_TAG = "SUB: ENDPOINT:";



    @Override
    protected Joke doInBackground(JokeLibrary... params) {
        if (jokesApi == null) {
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Set URL to 10.0.2.2:8080 on Default emulator
                    // Set URL to 10.0.3.2:8080 on GenyMotion
                    .setRootUrl("http://10.0.3.2:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            jokesApi = builder.build();

        }



        Joke joke = new Joke();






        try {

            Random rnd = new Random();
            List<Joke> jokeList = jokesApi.list().execute().getItems();
            Joke randomJoke = jokeList.get(rnd.nextInt(jokeList.size()));
            Log.i(LOG_TAG, "JOKE LIST SIZE 2... " + jokeList.size());

            Log.i(LOG_TAG, "SJoke 2= " + randomJoke.getJQuestion());

            return randomJoke;
        } catch (IOException e) {
//
            Log.i(LOG_TAG,"Something went wrong with joke..");
        }


        return joke;
    }

    @Override
    protected void onPostExecute(Joke joke) {
        super.onPostExecute(joke);

        SendJokeEvent newJoke = new SendJokeEvent();
        newJoke.setJoke(joke);
        try {
            EventBus.getDefault().post(newJoke);
        } catch (NullPointerException e) {
            Log.e(LOG_TAG,"Null pointer error when posting joke.");
            Joke nJoke = new Joke();
            nJoke.setJQuestion("Why did the chicken cross the road?");
            nJoke.setJAnswer("To Get to the other side!");
            EventBus.getDefault().post(nJoke);

        }





    }
}
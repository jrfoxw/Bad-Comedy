package com.foxbard.pydev.joketeller.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.example.JokeLibrary;

import com.example.JokeModel;
import com.example.pydev.myapplication.backend.jokeApi.JokeApi;
import com.example.pydev.myapplication.backend.jokeApi.model.Joke;
import com.foxbard.pydev.joketeller.main.messages.SendJokeEvent;
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
    String LOG_TAG = "APP: ENDPOINT:";
    Boolean jokesProcessed = false;
    Boolean jokesNotNull = false;
    Random rnd = new Random();


    @Override
    protected Joke doInBackground(JokeLibrary... params) {
        if (jokesApi == null) {
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Set URL to 10.0.2.2:8080 on default emulator
                    // Set URL to 10.0.3.2:8080 on GenyMotion

                    .setRootUrl("http://10.0.3.2:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

              // For Google Cloud...
//            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null).setRootUrl("https://jokesapi-142316.appspot.com/_ah/api/");

            jokesApi = builder.build();
            JokeLibrary jokeLibrary = new JokeLibrary();
            insertJokes(jokeLibrary);

            try {


                List<Joke> jokeList = jokesApi.list().execute().getItems();
                Log.i(LOG_TAG, "JokeList: " + jokeList);
                int sizeList = jokeList.size();
                Log.i(LOG_TAG, "JOKE LIST SIZE... " + sizeList);
                Joke randomJoke = jokeList.get(rnd.nextInt(jokeList.size()));
                return randomJoke;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;


//        Joke joke = new Joke();
//        JokeLibrary jokeLibrary = new JokeLibrary();
//
//
//        try {
//            try {
//                Thread.sleep(60);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            List<Joke> jokeList = jokesApi.list().execute().getItems();
//            try{
//                int sizeList = jokeList.size();
//            }catch(NullPointerException n){
//                Log.i(LOG_TAG, "Joke list is null..");
//                insertJokes(jokeLibrary);
//                Joke randomJoke = jokeList.get(rnd.nextInt(jokeList.size()));
//                Log.i(LOG_TAG, "JOKE LIST SIZE... " + jokeList.size());
//
//                Log.i(LOG_TAG, "SJoke = " + randomJoke.getJQuestion());
//
//                jokesApi.list().execute().clear();
//
//
//                try {
//                    Thread.sleep(60);
//                    return randomJoke;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
////                if(!jokesProcessed) {
////
////                    insertJokes(jokeLibrary);
////                    jokesProcessed = true;
////                }
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//


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
            EventBus.getDefault().post(newJoke);

        }


    }

    protected void insertJokes(JokeLibrary jokeLibrary) {


        for (JokeModel j : jokeLibrary.getListOJokes()) {
            Joke joke = new Joke();

            joke.setJQuestion(j.getjQuestion());
            joke.setJAnswer(j.getjAnswer());


            try {
                jokesApi.insert(joke).execute();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error when adding Data to GCE");
            }
        }

    }

}




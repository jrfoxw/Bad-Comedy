package com.foxbard.pydev.subactivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.util.Pair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.JokeLibrary;
import com.foxbard.pydev.subactivity.messages.SendJokeEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.common.io.Resources;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SubActivity extends AppCompatActivity {

    //TODO: Replace with dependency injection ButterKnife (Future)

    public MediaPlayer mp3Laugh, mp3Sting;
    public TextView tQuestion;
    public TextView tAnswer;
    public Button bNewJoke;
    public Button bAnswer;
    public ProgressBar mProgress;
    public int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    final ArrayList<String> rndRamblings = new ArrayList<>();
    final Random rnd = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        EventBus.getDefault().register(this);


        mp3Laugh = MediaPlayer.create(SubActivity.this, R.raw.laugh);
        mp3Sting = MediaPlayer.create(SubActivity.this, R.raw.jokesting);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);

        Collections.addAll(rndRamblings,"Looking for a joke.","Looking for a joke..","Looking for a joke...",
                "Looking for a joke....");


        tQuestion = (TextView) findViewById(R.id.tQuestion);
        tAnswer = (TextView) findViewById(R.id.tAnswer);
        final Button bNewJoke = (Button) findViewById(R.id.bNewJoke);
        final Button bAnswer = (Button) findViewById(R.id.bAnswer);




        Intent i = getIntent();



        tAnswer.setText(i.getStringExtra("Question"));
        tQuestion.setText(i.getStringExtra("Answer"));






        bNewJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processData();
                mProgress.setVisibility(View.VISIBLE);
                tQuestion.setText(rndRamblings.get(0));
                tAnswer.setVisibility(View.INVISIBLE);
                bAnswer.setVisibility(View.VISIBLE);
                bNewJoke.setVisibility(View.INVISIBLE);



//                new EndpointsAsyncTask().execute();


//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while(mProgressStatus <100){
//                            mProgressStatus +=1;
//
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mProgress.setProgress(mProgressStatus);
//
//                                }
//                            });
//
//                        }
//                        new EndpointsAsyncTask().execute();
//
//                    }
//
//                }).start();



            }
        });


                bAnswer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bAnswer.setVisibility(View.GONE);
                        tAnswer.setVisibility(View.VISIBLE);

                        if(!mp3Laugh.isPlaying()) {
                            mp3Laugh.start();
                        }
                        mp3Sting.start();
                        bNewJoke.setVisibility(View.VISIBLE);
//                        mProgress.setVisibility(View.INVISIBLE);

                    }
                });


            }



    @Subscribe
    public void onEvent(SendJokeEvent event) {
        Log.i("Something Happened", "Event Fired: " + event.getJoke());
        tQuestion.setText(event.getJoke().getJAnswer());
        tAnswer.setText(event.getJoke().getJQuestion());

    }


    public void processData(){



        new Thread(new Runnable() {





            @Override
            public void run() {
                while(mProgressStatus < 3){
                    mProgressStatus++;
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                            tQuestion.setText(rndRamblings.get(mProgressStatus));

                        }

                    });




                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.setVisibility(View.INVISIBLE);
                        mProgressStatus = 0;
                    }
                });
                new EndpointsAsyncTask().execute();


//                finish();
            }
        }).start();

    }

}


package com.foxbard.pydev.joketeller.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import com.example.JokeModel;
//import com.example.JokeLibrary;
import com.foxbard.pydev.joketeller.R;
import com.foxbard.pydev.joketeller.main.messages.SendJokeEvent;
import com.foxbard.pydev.subactivity.SubActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    public MediaPlayer mp;

    Button bLaunchIntent;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Bad Comedy");

        bLaunchIntent = (Button) findViewById(R.id.bLaunchIntent);
        imageView = (ImageView) findViewById(R.id.imageView2);

        EventBus.getDefault().register(this);

        mp = MediaPlayer.create(this, R.raw.fluffyrufflesxylophone);
        mp.setLooping(true);
        mp.start();



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new EndpointsAsyncTask().execute();


            }
        });
    }

    @Subscribe
    public void onEvent(SendJokeEvent event){
        Log.i("Joke Grabber","Found a Joke!: "+event.getJoke());

        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        intent.putExtra("Question",event.getJoke().getJQuestion());
        intent.putExtra("Answer", event.getJoke().getJAnswer());
        startActivity(intent);


    }
}

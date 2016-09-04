package com.foxbard.pydev.joketeller.main;


import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.foxbard.pydev.joketeller.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LoadActivity extends AppCompatActivity {



    int timer = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);


        final Handler handler = new Handler(Looper.getMainLooper());
        final TextView progressTextView = (TextView) findViewById(R.id.progressTextView);
        final Random rnd = new Random();
        final ArrayList<String> rndRamblings = new ArrayList<>();
        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);

        Collections.addAll(rndRamblings,"Just looking for joke..","Getting Bored..","Still looking",
                "Rectifying Androids..","Changing the alignment of the sun..","Adjusting Screen..",
                "Sharpening Dracula's fangs..","Still going...","Thundering the Cats...","Stay on Target..",
                "Randomizing Randomness...","Facilitating Factors...");








        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {





            @Override
            public void run() {
                while(timer < 15){
                    timer++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(timer);
                            progressTextView.setText(rndRamblings
                                    .get(rnd.nextInt(rndRamblings.size())));

                        }

                    });




                }
//
                Intent i = new Intent(LoadActivity.this, MainActivity.class);
                startActivity(i);
              finish();
            }
        }).start();


    }






}

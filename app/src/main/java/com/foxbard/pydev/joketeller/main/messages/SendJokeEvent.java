package com.foxbard.pydev.joketeller.main.messages;

import com.example.pydev.myapplication.backend.jokeApi.model.Joke;

/**
 * Created by PY-DEV on 8/31/2016.
 */
public class SendJokeEvent {

    Joke joke;


    public Joke getJoke(){
        return joke;
    }

   public void setJoke(Joke joke){
        this.joke = joke;
    }


}

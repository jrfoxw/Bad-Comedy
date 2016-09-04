package com.example;

import java.util.ArrayList;

/**
 * Created by PY-DEV on 8/28/2016.
 */
public class JokeLibrary {

    ArrayList<JokeModel> listOJokes = new ArrayList<>();


    public JokeLibrary(){}


    public ArrayList<JokeModel> getListOJokes() {
        listOJokes.add(new JokeModel("Why did the chicken cross the road?", "To Get to the other side!"));
        listOJokes.add(new JokeModel("Why did the computer go to the doctor?", "Because it had a virus!"));
        listOJokes.add(new JokeModel("What do you get from a pandered cow?", "Spoiled Milk!"));
        listOJokes.add(new JokeModel("What gets wetter the more it dries?", "A towel!"));
        listOJokes.add(new JokeModel("Where do snowmen keep their money?", "In a snowbank!"));
        listOJokes.add(new JokeModel("What is black, white and red all over?", "A newspaper!"));
        listOJokes.add(new JokeModel("What do you call a false noodle?", "An Impasta!"));
        listOJokes.add(new JokeModel("What did the pencil say to the other pencil?", "'You are looking sharp!'"));
        listOJokes.add(new JokeModel("Why did the picture go to jail?","Because it was framed!"));
        listOJokes.add(new JokeModel("What do you call a baby monkey?","A 'Chimp' off the old block!"));
        listOJokes.add(new JokeModel("What stays in the corner and travels all over the world?","A postage stamp!"));
        listOJokes.add(new JokeModel("What do you call a belt with a watch on it?","A Waist of time!"));
        listOJokes.add(new JokeModel("What washes up on very small beaches?","Microwaves!"));
        listOJokes.add(new JokeModel("What did the candle say to the other candle","I'm going out tonight"));
        listOJokes.add(new JokeModel("Why can't a nose be 12 inches long?","Because then it would be a foot!"));
        listOJokes.add(new JokeModel("What has four wheels and flies?","A garbage truck!"));
        listOJokes.add(new JokeModel("What did the triangle say to the circle?","Your pointless!"));


        return listOJokes;
    }
}

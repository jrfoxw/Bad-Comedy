package com.example.pydev.myapplication.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by PY-DEV on 8/27/2016.
 */
public class OfyService {

    /**
     * Objectify service wrapper so we can statically register our persistence classes
     * More on Objectify here : https://code.google.com/p/objectify-appengine/
     *
     */

        static {
            ObjectifyService.register(Joke.class);
        }
        public static Objectify ofy() {
            return ObjectifyService.ofy();
        }
        public static ObjectifyFactory factory() {
            return ObjectifyService.factory();
        }

}

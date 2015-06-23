package com.krypto.backend;

import com.example.MyClass;

/**
 * Base class for supplying jokes
 */
public class Joke {

    MyClass myClass;

    public Joke() {
        myClass = new MyClass();
    }

    public String gettheJoke() {
        return myClass.getJoke();
    }
}

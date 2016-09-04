package com.example.pydev.myapplication.backend;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Joke {

    @Id
    Long id;
    String jQuestion;
    String jAnswer;

    public Joke() {}

    public Joke(String jAnswer, String jQuestion) {

        this.jQuestion = jQuestion;
        this.jAnswer = jAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getjQuestion() {
        return jQuestion;
    }

    public void setjQuestion(String jQuestion) {
        this.jQuestion = jQuestion;
    }

    public String getjAnswer() {
        return jAnswer;
    }

    public void setjAnswer(String jAnswer) {
        this.jAnswer = jAnswer;
    }
}





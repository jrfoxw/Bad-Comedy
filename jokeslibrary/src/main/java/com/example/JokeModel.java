package com.example;




public class JokeModel {


    Long id;
    String jQuestion;
    String jAnswer;

    public JokeModel() {}

    public JokeModel(String jAnswer, String jQuestion) {

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





package com.example.finance.service;

/**
 * Question Web.
 */
public class QuestionInfoWeb {
    private int number;
    private int type;
    private String question;
    private String choices[];

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }
}

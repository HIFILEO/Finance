package com.example.finance.service;

/**
 * Web response
 */
public class ScreenInfoWeb {
    private QuestionInfoWeb[] questions;
    private TakeAwayInfoWeb[] takeAways;
    private SkipInfoWeb[] skips;

    public QuestionInfoWeb[] getQuestionInfoWebs() {
        if (questions == null) {
            questions = new QuestionInfoWeb[0];
        }
        return questions;
    }

    public TakeAwayInfoWeb[] getTakeAwayInfoWebs() {
        if (takeAways == null) {
            takeAways = new TakeAwayInfoWeb[0];
        }
        return takeAways;
    }

    public SkipInfoWeb[] getSkipInfoWebs() {
        if (skips == null) {
            skips = new SkipInfoWeb[0];
        }
        return skips;
    }
}

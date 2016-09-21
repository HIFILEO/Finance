package com.example.finance.model;

/**
 * Skip data.
 */
public class Skip {
    private final int questionNumber;
    private final int answerNumber;

    public Skip(int questionNumber, int answerNumber) {
        this.questionNumber = questionNumber;
        this.answerNumber = answerNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }
}

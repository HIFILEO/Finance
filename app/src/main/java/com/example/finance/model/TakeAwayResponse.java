package com.example.finance.model;

/**
 * Take away responses.
 */
public class TakeAwayResponse {
    private final int questionNumber;
    private final int answer;

    public TakeAwayResponse(int questionNumber, int answer) {
        this.questionNumber = questionNumber;
        this.answer = answer;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getAnswer() {
        return answer;
    }
}

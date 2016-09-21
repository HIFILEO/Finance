package com.example.finance.model;

/**
 * Numerical answer.
 */
public class NumericalAnswer implements Answer{
    private final int answer;

    public NumericalAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public int getType() {
        return QuestionType.NUMERICAL;
    }

    public int getAnswer() {
        return answer;
    }
}

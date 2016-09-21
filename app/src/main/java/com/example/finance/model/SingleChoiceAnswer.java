package com.example.finance.model;

/**
 * Single choice answer.
 */
public class SingleChoiceAnswer implements Answer {
    private final int answer;

    public SingleChoiceAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public int getType() {
        return QuestionType.SINGLE_CHOICE;
    }

    public int getAnswer() {
        return answer;
    }
}

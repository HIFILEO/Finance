package com.example.finance.model;

/**
 * Text based answer.
 */
public class TextAnswer implements Answer {
    private final String answer;

    public TextAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int getType() {
        return QuestionType.TEXTUAL;
    }

    public String getAnswer() {
        return answer;
    }
}

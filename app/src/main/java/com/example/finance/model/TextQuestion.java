package com.example.finance.model;

/**
 * Text based question.
 */
public class TextQuestion extends Question {
    private final int questionNumber;
    private final String question;

    public TextQuestion(int questionNumber, String question) {
        this.questionNumber = questionNumber;
        this.question = question;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public @QuestionType int getType() {
        return QuestionType.TEXTUAL;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}

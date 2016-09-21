package com.example.finance.model;

/**
 * Numerical Question.
 */
public class NumericalQuestion extends Question {
    private final int questionNumber;
    private final String question;

    public NumericalQuestion(int questionNumber, String question) {
        this.questionNumber = questionNumber;
        this.question = question;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public @QuestionType int getType() {
        return QuestionType.NUMERICAL;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}

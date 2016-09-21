package com.example.finance.model;

/**
 * Single choice question.
 */
public class SingleChoiceQuestion extends Question {
    private final int questionNumber;
    private final String question;
    private final String[] choices;

    public SingleChoiceQuestion(int questionNumber, String question, String[] choices) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.choices = choices;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public @QuestionType int getType() {
        return QuestionType.SINGLE_CHOICE;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }
}

package com.example.finance.model;

/**
 * Multiple choice question.
 */
public class MultipleChoiceQuestion extends Question {
    private final int questionNumber;
    private final String question;
    private final String[] choices;

    public MultipleChoiceQuestion(int questionNumber, String question, String[] choices) {
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
        return QuestionType.MULTIPLE_CHOICE;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public String[] getChoices() {
        return choices;
    }
}

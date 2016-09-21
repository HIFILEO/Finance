package com.example.finance.model;

/**
 * Base question class.
 */
public abstract class Question implements Comparable {
    public abstract int getQuestionNumber();
    public abstract @QuestionType int getType();
    public abstract String getQuestion();

    @Override
    public int compareTo(Object another) {
        Question anotherQuestion = (Question) another;
        if (this.getQuestionNumber() < anotherQuestion.getQuestionNumber()) {
            return -1;
        }else if (this.getQuestionNumber() > anotherQuestion.getQuestionNumber()) {
            return +1;
        }

        return 0;
    }
}

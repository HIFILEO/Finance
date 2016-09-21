package com.example.finance.model;

import java.util.Set;

/**
 * Multiple choice answer.
 */
public class MultipleChoiceAnswer implements Answer{
    private Set<Integer> answers;

    public MultipleChoiceAnswer(Set<Integer> answers) {
        this.answers = answers;
    }

    @Override
    public int getType() {
        return QuestionType.MULTIPLE_CHOICE;
    }

    public Set<Integer> getAnswers() {
        return answers;
    }
}

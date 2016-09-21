package com.example.finance.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages the app data.
 */
public class DataManager {
    //Key - screen number , value - List of question numbers
    private Map<Integer, Set<Integer>> screenMap = new HashMap<>();

    //Key - question number, value - Question
    private Map<Integer, Question> questionMap = new HashMap<>();

    //Key - question number, value - answer
    private Map<Integer, Answer> answerMap = new HashMap<>();

    //Key - screen number, value - list of take aways
    private Map<Integer, List<TakeAway>> takeAwayMap = new HashMap<>();

    //Key - screen number, value - list of skips
    private Map<Integer, List<Skip>> skipMap = new HashMap<>();

    public void addQuestion(int screenNumber, @NonNull Question question) {
        //
        //Address Screen Map
        //
        Set<Integer> questionNumberSet = screenMap.get(screenNumber);
        if (questionNumberSet == null) {
            questionNumberSet = new HashSet<>();
        }

        questionNumberSet.add(question.getQuestionNumber());
        screenMap.put(screenNumber, questionNumberSet);

        //
        //Address Question Map
        //
        questionMap.put(question.getQuestionNumber(), question);
    }

    public void setAnswer(int questionNumber, @NonNull Answer answer) {
        answerMap.put(questionNumber, answer);
    }

    public void removeAnswer(int questionNumber) {
        answerMap.remove(questionNumber);
    }

    public void addTakeAway(int screenNumber, @NonNull TakeAway takeAway) {
        List<TakeAway> takeAwayList = takeAwayMap.get(screenNumber);
        if (takeAwayList == null) {
            takeAwayList = new ArrayList<>();
        }

        takeAwayList.add(takeAway);
        takeAwayMap.put(screenNumber, takeAwayList);
    }

    public void addSkip(int screenNumber, @NonNull Skip skip) {
        List<Skip> skipList = skipMap.get(screenNumber);
        if (skipList == null) {
            skipList = new ArrayList<>();
        }

        skipList.add(skip);
        skipMap.put(screenNumber, skipList);
    }

    /**
     * Get questions for specific screen.
     * @param screenNumber
     * @return
     */
    public @Nullable List<Question> getQuestions(int screenNumber) {
        List<Question> questionList = null;

        Set<Integer> questionNumberSet = screenMap.get(screenNumber);
        if (questionNumberSet != null) {
            questionList = new ArrayList<>();

            for (Integer i : questionNumberSet) {
                questionList.add(questionMap.get(i));
            }

            Collections.sort(questionList);
        }

        return questionList;
    }

    /**
     * Get a question given a specific question number
     * @param questionNumber
     * @return
     */
    public @Nullable  Question getQuestion(int questionNumber) {
        return questionMap.get(questionNumber);
    }

    public @Nullable Answer getAnswer(int questionNumber) {
        return answerMap.get(questionNumber);
    }

    public @Nullable List<TakeAway> getTakeAways(int screenNumber) {
        return takeAwayMap.get(screenNumber);
    }

    public @Nullable List<Skip> getSkips(int screenNumber) {
        return skipMap.get(screenNumber);
    }
}

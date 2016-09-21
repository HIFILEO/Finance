package com.example.finance.presenter;

import com.example.finance.model.Question;

import java.util.List;

/**
 * View interface to be implemented by the forward facing UI part of android. An activity or fragment.
 */
public interface InfoViewModel {
    void showInProgress(boolean show);
    void showError();
    void setAdapter(List<Question> questionList);
    void setNameTextField(String name);
    void gotToFinalScreen();
    void showTakeAway(String textToShow);
    void hideTakeAway();
}

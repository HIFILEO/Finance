package com.example.finance.adapter;

/**
 * Listener to hook view holder and adapter so adapter can keep track of completed questions in list.
 */
public interface OnQuestionCompletedListener {
    void onComplete(int questionNumber);
    void onIncomplete(int questionNumber);
}

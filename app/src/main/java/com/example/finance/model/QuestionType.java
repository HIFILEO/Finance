package com.example.finance.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Question types that are used internally.
 */
@IntDef({QuestionType.TEXTUAL, QuestionType.NUMERICAL, QuestionType.SINGLE_CHOICE, QuestionType.MULTIPLE_CHOICE})
@Retention(RetentionPolicy.SOURCE)
@SuppressWarnings("checkstyle:abbreviationaswordinname")
public @interface QuestionType {
    int TEXTUAL = 1;
    int NUMERICAL = 2;
    int SINGLE_CHOICE = 3;
    int MULTIPLE_CHOICE = 4;
}

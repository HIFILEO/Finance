package com.example.finance.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Manages the actions that a screen can have, like take aways and skips.
 */
public class ScreenManager {
    private final DataManager dataManager;

    public ScreenManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Determine if there is a take away for the current screen
     * @param screenNumber - screen number
     * @return String to show for takeaway, null otherwise
     */
    public @Nullable String determineTakeAway(int screenNumber) {
        List<TakeAway> takeAwayList= dataManager.getTakeAways(screenNumber);

        if (takeAwayList == null) {
            return null;
        }

        for (TakeAway takeAway : takeAwayList) {
            for (TakeAwayResponse takeAwayResponse : takeAway.getResponses()) {
                Answer answer = dataManager.getAnswer(takeAwayResponse.getQuestionNumber());
                if (answer != null) {
                    switch (answer.getType()) {
                        case QuestionType.NUMERICAL:
                            NumericalAnswer numericalAnswer = (NumericalAnswer) answer;
                            if (numericalAnswer.getAnswer() == takeAwayResponse.getAnswer()) {
                                return takeAway.getTakeAway();
                            }
                        case QuestionType.SINGLE_CHOICE:
                            SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) answer;
                            if (singleChoiceAnswer.getAnswer() == takeAwayResponse.getAnswer()) {
                                return takeAway.getTakeAway();
                            }
                            break;
                        case QuestionType.MULTIPLE_CHOICE:
                            MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) answer;
                            if (multipleChoiceAnswer.getAnswers().contains(takeAwayResponse.getAnswer())) {
                                return takeAway.getTakeAway();
                            }
                        case QuestionType.TEXTUAL:
                        default:
                            break;
                    }
                }

            }
        }

        return null;
    }

    /**
     * Determine if there screen should be skipped
     * @param screenNumber - current Screen number
     * @return true to skip, false otherwise
     */
    public boolean determineSkip(int screenNumber) {
        List<Skip> skips = dataManager.getSkips(screenNumber);
        if (skips != null) {
            for (Skip skip : skips) {
                Answer answer = dataManager.getAnswer(skip.getQuestionNumber());
                if (answer != null) {
                    switch (answer.getType()) {
                        case QuestionType.SINGLE_CHOICE:
                            SingleChoiceAnswer singleChoiceAnswer = (SingleChoiceAnswer) answer;
                            if (singleChoiceAnswer.getAnswer() == skip.getAnswerNumber()) {
                                return true;
                            }
                            break;
                        case QuestionType.MULTIPLE_CHOICE:
                            MultipleChoiceAnswer multipleChoiceAnswer = (MultipleChoiceAnswer) answer;
                            if (multipleChoiceAnswer.getAnswers().contains(skip.getAnswerNumber())) {
                                return true;
                            }
                        case QuestionType.TEXTUAL:
                        case QuestionType.NUMERICAL:
                        default:
                            //Only multiple choice handled...
                            break;
                    }
                }
            }
        }

        return false;
    }
}

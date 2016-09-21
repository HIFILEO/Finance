package com.example.finance.presenter;

import android.content.Context;

import com.example.finance.R;
import com.example.finance.interactor.InfoInteractor;
import com.example.finance.interactor.InfoResponseModel;
import com.example.finance.model.DataManager;
import com.example.finance.model.Question;
import com.example.finance.model.ScreenManager;
import com.example.finance.model.TextAnswer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Implements the Presenter interface.
 */
public class InfoPresenterImpl implements InfoPresenter, InfoResponseModel {
    private InfoViewModel infoViewModel;
    private InfoInteractor infoInteractor;
    private DataManager dataManager;
    private ScreenManager screenManager;
    private Context context;
    private int currentScreen;

    public InfoPresenterImpl(InfoInteractor infoInteractor, InfoViewModel infoViewModel, DataManager dataManager,
                             ScreenManager screenManager, Context context) {
        this.infoInteractor = infoInteractor;
        this.infoViewModel = infoViewModel;
        this.screenManager = screenManager;
        this.dataManager = dataManager;
        this.context = context;
    }

    @Override
    public void loadInfo() {
        infoInteractor.loadInfo();
        infoViewModel.showInProgress(true);
    }

    @Override
    public void start() {
        this.infoInteractor.setInfoResponseModel(this);
    }

    @Override
    public void next() {
        //
        //Show name
        //
        if (currentScreen == 0) {
            TextAnswer textAnswer = (TextAnswer) dataManager.getAnswer(0);
            String strNextResultsSize = context.getResources().getString(R.string.welcome_text, textAnswer.getAnswer());
            infoViewModel.setNameTextField(strNextResultsSize);
        }

        //
        //Determine take away
        //
        boolean takeAway = determineTakeAway();

        //
        //Determine skip
        //
        boolean skip = false;
        if (!takeAway) {
            skip = determineSkip();
        }

        //
        //Determine next screen
        //
        if (!takeAway) {
            if (skip) {
                currentScreen = currentScreen + 2;
            } else {
                currentScreen = currentScreen + 1;
            }

            List<Question> questionList = dataManager.getQuestions(currentScreen);
            if (questionList == null) {
                infoViewModel.gotToFinalScreen();
            } else {
                infoViewModel.setAdapter(questionList);
            }
        }
    }

    @Override
    public void infoLoaded() {
        currentScreen = 0;

        infoViewModel.showInProgress(false);
        infoViewModel.setAdapter(dataManager.getQuestions(currentScreen));
    }

    @Override
    public void errorLoadingInfoData() {
        infoViewModel.showError();
    }

    /**
     * Determine take away, if so start background thread.
     * @return true when take away needs to show, false otherwise
     */
    private boolean determineTakeAway() {
        String takeAway = screenManager.determineTakeAway(currentScreen);
        if (takeAway != null) {
            //Start Timer for take away
            Observable.timer(3000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            infoViewModel.hideTakeAway();
                            currentScreen++;
                            infoViewModel.setAdapter(dataManager.getQuestions(currentScreen));
                        }
                    });

            infoViewModel.showTakeAway(takeAway);
            return true;
        }

        return false;
    }

    /**
     * Determine if we need to skip
     * @return true if current screen needs to be skipped, false otherwise
     */
    private boolean determineSkip() {
        return screenManager.determineSkip(currentScreen);
    }
}


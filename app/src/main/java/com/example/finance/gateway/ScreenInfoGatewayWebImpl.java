package com.example.finance.gateway;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;


import com.example.finance.model.DataManager;
import com.example.finance.model.MultipleChoiceQuestion;
import com.example.finance.model.NumericalQuestion;
import com.example.finance.model.Question;
import com.example.finance.model.SingleChoiceQuestion;
import com.example.finance.model.Skip;
import com.example.finance.model.TakeAway;
import com.example.finance.model.TakeAwayResponse;
import com.example.finance.model.TextQuestion;
import com.example.finance.service.QuestionInfoWeb;
import com.example.finance.service.ScreenInfoResponseWeb;
import com.example.finance.service.ScreenInfoServiceApi;
import com.example.finance.service.ScreenInfoWeb;
import com.example.finance.service.SkipInfoWeb;
import com.example.finance.service.TakeAwayInfoWeb;
import com.example.finance.service.TakeAwayResponseInfoWeb;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Implementation of GatewayInterface when loading data from web.
 */
public class ScreenInfoGatewayWebImpl implements ScreenInfoGateway {
    private final DataManager dataManager;
    private final ScreenInfoServiceApi screenInfoServiceApi;

    public ScreenInfoGatewayWebImpl(ScreenInfoServiceApi screenInfoServiceApi, DataManager dataManager) {
        this.screenInfoServiceApi = screenInfoServiceApi;
        this.dataManager = dataManager;
    }

    @Override
    public Observable<Boolean> loadInfo() {
        /*
        Notes - Load data from web on scheduler thread. Translate the web response to our internal business response
        on computation thread. Return observable. Not going to worry about errors at this time....
         */
        return screenInfoServiceApi.getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap(new LoadScreenInfoSubscriptionFunc1(dataManager))
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("Error: " + throwable.getLocalizedMessage());
                    }
                });
    }

    @VisibleForTesting
    public static class LoadScreenInfoSubscriptionFunc1 implements Func1<ScreenInfoResponseWeb, Observable<Boolean>> {
        private DataManager dataManager;

        public LoadScreenInfoSubscriptionFunc1(DataManager dataManager) {
            this.dataManager = dataManager;
        }

        @Override
        public Observable<Boolean> call(ScreenInfoResponseWeb screenInfoResponseWeb) {
            //
            //Guard
            //
            if (screenInfoResponseWeb == null || screenInfoResponseWeb.getScreenInfoArray() == null ||
                    screenInfoResponseWeb.getScreenInfoArray().length == 0) {
                return Observable.just(false);
            }

            //
            //Translate
            //
            for (int i = 0; i < screenInfoResponseWeb.getScreenInfoArray().length; i++) {
                ScreenInfoWeb screenInfoWeb = screenInfoResponseWeb.getScreenInfoArray()[i];

                //
                //Address Question
                //
                for (QuestionInfoWeb questionInfoWeb : screenInfoWeb.getQuestionInfoWebs()) {
                    Question question = translateQuestion(questionInfoWeb);
                    if (question != null) {
                        dataManager.addQuestion(i, question);
                    }
                }

                //
                //Address Take Away
                //
                for (TakeAwayInfoWeb takeAwayInfoWeb : screenInfoWeb.getTakeAwayInfoWebs()) {
                    TakeAway takeAway = translateTakeAway(takeAwayInfoWeb);
                    dataManager.addTakeAway(i, takeAway);
                }

                //
                //Address Skip
                //
                for (SkipInfoWeb skipInfoWeb : screenInfoWeb.getSkipInfoWebs()) {
                    Skip skip = translateSkip(skipInfoWeb);
                    dataManager.addSkip(i, skip);
                }
            }

            return Observable.just(true);
        }

        @VisibleForTesting
        public @Nullable Question translateQuestion(@NonNull QuestionInfoWeb questionInfoWeb) {
            switch (questionInfoWeb.getType()) {
                case 1:
                    return new TextQuestion(questionInfoWeb.getNumber(), questionInfoWeb.getQuestion());
                case 2:
                    return new NumericalQuestion(questionInfoWeb.getNumber(), questionInfoWeb.getQuestion());
                case 3:
                    return new SingleChoiceQuestion(questionInfoWeb.getNumber(), questionInfoWeb.getQuestion(),
                            questionInfoWeb.getChoices());
                case 4:
                    return new MultipleChoiceQuestion(questionInfoWeb.getNumber(), questionInfoWeb.getQuestion(),
                            questionInfoWeb.getChoices());
                default:
                    return null;
            }
        }

        @VisibleForTesting
        public @NonNull TakeAway translateTakeAway(@NonNull TakeAwayInfoWeb takeAwayInfoWeb) {
            List<TakeAwayResponse> responses = new ArrayList<>();
            for (TakeAwayResponseInfoWeb takeAwayResponseInfoWeb : takeAwayInfoWeb.getResponses()) {
                responses.add(new TakeAwayResponse(takeAwayResponseInfoWeb.getQuestionNumber(),
                        takeAwayResponseInfoWeb.getAnswer()));
            }

            return new TakeAway(takeAwayInfoWeb.getTakeAway(), responses);
        }

        @VisibleForTesting
        public @NonNull Skip translateSkip(@NonNull  SkipInfoWeb skipInfoWeb) {
            return new Skip(skipInfoWeb.getQuestionNumber(), skipInfoWeb.getAnswerNumber());
        }
    }
}

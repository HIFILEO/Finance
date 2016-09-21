package com.example.finance.interactor;

import android.support.annotation.VisibleForTesting;

import com.example.finance.gateway.ScreenInfoGateway;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Info Interactor Implementation.
 */
public class InfoInteractorImpl implements InfoInteractor {
    private InfoResponseModel infoResponseModel;
    private ScreenInfoGateway screenInfoGateway;

    public InfoInteractorImpl(ScreenInfoGateway screenInfoGateway) {
        this.screenInfoGateway = screenInfoGateway;
    }

    @Override
    public void setInfoResponseModel(InfoResponseModel infoResponseModel) {
        this.infoResponseModel = infoResponseModel;
    }

    @Override
    public void loadInfo() {
        screenInfoGateway.loadInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new LoadInfoSubscriptionAction1(this.infoResponseModel),
                        new LoadInfoSubscriptionErrorAction1(this.infoResponseModel));
    }

    /**
     * Visible for testing that a load info subscription does the right thing.
     */
    @VisibleForTesting
    public static class LoadInfoSubscriptionAction1 implements Action1<Boolean> {
        private InfoResponseModel infoResponseModel;

        public LoadInfoSubscriptionAction1(InfoResponseModel infoResponseModel) {
            this.infoResponseModel = infoResponseModel;
        }

        @Override
        public void call(Boolean aBoolean) {
            if (infoResponseModel != null) {
                if (aBoolean) {
                    infoResponseModel.infoLoaded();
                } else {
                    infoResponseModel.errorLoadingInfoData();
                }
            }

            /*
            Note - An interactor has interactions with many objects in order to complete a task. Any other interactions
            would be placed here.
             */
        }
    }

    /**
     * Visible for testing that a load info subscription does the right thing during an error.
     */
    @VisibleForTesting
    public static class LoadInfoSubscriptionErrorAction1 implements Action1<Throwable> {
        private InfoResponseModel infoResponseModel;

        public LoadInfoSubscriptionErrorAction1(InfoResponseModel infoResponseModel) {
            this.infoResponseModel = infoResponseModel;
        }

        @Override
        public void call(Throwable throwable) {
            Timber.e("Error: " + throwable.getLocalizedMessage());
            if (infoResponseModel != null) {
                infoResponseModel.errorLoadingInfoData();
            }
        }
    }
}

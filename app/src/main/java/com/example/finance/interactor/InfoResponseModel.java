package com.example.finance.interactor;

/**
 * Response Model - how the Interactor communicates with presenter.
 */
public interface InfoResponseModel {
    void infoLoaded();
    void errorLoadingInfoData();
}

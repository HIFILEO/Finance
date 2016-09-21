package com.example.finance.gateway;


import rx.Observable;

/**
 * The Gateway Interface
 */
public interface ScreenInfoGateway {
    Observable<Boolean> loadInfo();
}

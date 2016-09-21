package com.example.finance.service;

import android.content.Context;

import com.example.finance.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func0;

/**
 * Loads screen data from raw, allows me to release the app without the need of back end server data, however
 * it's to be noted that retro fit is in place in case I don't wnat to use this and host the file somewhere.
 */
public class ScreenInfoServiceApiRawImpl implements ScreenInfoServiceApi {
    private Context context;

    public ScreenInfoServiceApiRawImpl(Context context) {
        this.context = context;
    }

    @Override
    public Observable<ScreenInfoResponseWeb> getInfo() {
        return Observable.defer(new Func0<Observable<ScreenInfoResponseWeb>>() {
            @Override
            public Observable<ScreenInfoResponseWeb> call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                InputStream inputStream = context.getResources().openRawResource(R.raw.screen_test);

                if (inputStream != null) {
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    Gson gson1 = new Gson();

                    ScreenInfoResponseWeb screenInfoResponseWeb = gson1.fromJson(reader, ScreenInfoResponseWeb.class);
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw Exceptions.propagate(new Throwable("Stream not closed correctly."));
                    }

                    return Observable.just(screenInfoResponseWeb);
                }

                return Observable.empty();
            }
        });
    }
}

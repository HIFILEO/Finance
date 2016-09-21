package com.example.finance.service;

/**
 * Take away web response.
 */
public class TakeAwayInfoWeb {
    private String takeAway;
    private TakeAwayResponseInfoWeb[] responses;

    public String getTakeAway() {
        return takeAway;
    }

    public TakeAwayResponseInfoWeb[] getResponses() {
        if (responses == null) {
            responses = new TakeAwayResponseInfoWeb[0];
        }
        return responses;
    }
}

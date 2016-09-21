package com.example.finance.model;

import java.util.List;

/**
 * Takeaway data.
 */
public class TakeAway {
    private final String takeAway;
    private List<TakeAwayResponse> responses;

    public TakeAway(String takeAway, List<TakeAwayResponse> responses) {
        this.takeAway = takeAway;
        this.responses = responses;
    }

    public String getTakeAway() {
        return takeAway;
    }

    public List<TakeAwayResponse> getResponses() {
        return responses;
    }
}

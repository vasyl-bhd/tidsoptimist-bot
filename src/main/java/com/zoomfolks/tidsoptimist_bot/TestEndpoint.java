package com.zoomfolks.tidsoptimist_bot;

import com.zoomfolks.tidsoptimist_bot.service.PollDataFiller;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestEndpoint {
    private final PollDataFiller pollDataFiller;

    public void get() {
        System.out.println(pollDataFiller.generatePollData() );
    }

}

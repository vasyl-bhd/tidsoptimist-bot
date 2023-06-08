package com.zoomfolks.tidsoptimist_bot;

import com.zoomfolks.tidsoptimist_bot.bot.pojo.PollData;
import com.zoomfolks.tidsoptimist_bot.service.PollDataFiller;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class TestEndpoint {
    private final PollDataFiller pollDataFiller;

    @PostConstruct
    public void get() {
        System.out.println(pollDataFiller.generatePollData() );
    }

}

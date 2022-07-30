package com.zoomfolks.tidsoptimist_bot.bot.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    public String getDetailedReport(String username, List<String> lates) {
        return username + System.lineSeparator() +
                "Total lates this week: " + lates.size() + System.lineSeparator() +
                "Details: " + System.lineSeparator() + String.join(System.lineSeparator(), lates);
    }

    public String getShortReport(String username, int lates) {
        return username + System.lineSeparator() +  "Total lates this week: " + lates;
    }
}

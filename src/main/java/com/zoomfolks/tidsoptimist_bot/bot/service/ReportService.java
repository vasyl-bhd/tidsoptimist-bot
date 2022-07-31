package com.zoomfolks.tidsoptimist_bot.bot.service;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReportService {

    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, hh:mm").localizedBy(Locale.ENGLISH);

    public String getReport(String username, List<LocalDateTime> lates) {
        return username + System.lineSeparator() +
                lates.stream()
                        .map(dtf::format)
                        .collect(Collectors.joining("\n"));
    }

}

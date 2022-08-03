package com.zoomfolks.tidsoptimist_bot.bot.service;


import com.zoomfolks.tidsoptimist_bot.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.DateUtils.REPORT_FORMATTER;
import static java.util.function.Predicate.not;

@Service
public class ReportService {

    public String getReport(String username, List<LocalDateTime> lates) {
        return username + System.lineSeparator() +
                lates.stream()
                        .filter(not(DateUtils::isWeekend))
                        .map(REPORT_FORMATTER::format)
                        .collect(Collectors.joining("\n"));
    }
}

package com.zoomfolks.tidsoptimist_bot.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class DateUtils {
    public static final DateTimeFormatter REPORT_FORMATTER = DateTimeFormatter.ofPattern("EE, dd.MM 'at' hh:mm")
            .localizedBy(Locale.ENGLISH);

    public static Instant startOfWeek() {
        return LocalDateTime.now()
                .minusWeeks(1)
                .with(LocalTime.MIN)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toInstant(ZoneOffset.UTC);
    }

    public static boolean isWeekend(LocalDateTime localDateTime) {
        switch (localDateTime.getDayOfWeek()) {
            case SATURDAY, SUNDAY -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
}

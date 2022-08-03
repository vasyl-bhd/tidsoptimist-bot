package com.zoomfolks.tidsoptimist_bot.service;

import com.zoomfolks.tidsoptimist_bot.dao.GuysDao;
import com.zoomfolks.tidsoptimist_bot.dao.entity.Guy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.DateUtils.startOfWeek;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GuysDaoHandler {
    private final GuysDao guysDao;

    public void logLate(String username) {
        var guy = new Guy();
        guy.setUsername(username);
        guy.setLateDate(Timestamp.valueOf(LocalDateTime.now()));

       guysDao.save(guy);
    }

    public List<LocalDateTime> getWeeklyStats(String username) {
        return guysDao.findAllByUsername(username)
                .stream()
                .filter(g -> g.getLateDate().toInstant().isAfter(startOfWeek()))
                .map(g -> g.getLateDate().toLocalDateTime())
                .collect(toList());
    }

    public Map<String, List<LocalDateTime>> getWeeklyStatsForAll() {
        return guysDao.findAll()
                .stream()
                .filter(g -> g.getLateDate().toInstant().isAfter(startOfWeek()))
                .collect(groupingBy(Guy::getUsername, Collectors.mapping(g -> g.getLateDate().toLocalDateTime(), toList())));
    }
}

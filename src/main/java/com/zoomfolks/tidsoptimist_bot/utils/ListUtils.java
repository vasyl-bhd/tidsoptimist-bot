package com.zoomfolks.tidsoptimist_bot.utils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListUtils {
    public static <T> T getRandomElement(List<T> list) {
        var randIdx = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(randIdx);
    }
}

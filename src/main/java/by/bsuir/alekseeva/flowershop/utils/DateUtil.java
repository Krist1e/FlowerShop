package by.bsuir.alekseeva.flowershop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private DateUtil() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
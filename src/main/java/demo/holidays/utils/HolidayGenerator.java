package demo.holidays.utils;

import java.util.*;
import java.util.stream.Collectors;

public class HolidayGenerator {

    private final static String NOT_FOUND = "No holidays found";

    private static Map<String, List<String>> holidays;

    static {
        holidays = new HashMap<>();
        holidays.put("jan", Arrays.asList("New Year", "Christmas"));
        holidays.put("mar", Arrays.asList("International Women's Day"));
        holidays.put("may", Arrays.asList("labor Day", "Victory Day"));
        holidays.put("aug", Arrays.asList("Independence Day"));
    }

    public static String getHolidays(String month) {
        if (Objects.isNull(month)) {
            return NOT_FOUND;
        }

        List<String> foundHolidays = holidays.getOrDefault(month.toLowerCase(), Collections.EMPTY_LIST);
        return foundHolidays.isEmpty() ? NOT_FOUND : foundHolidays.stream().collect(Collectors.joining(","));
    }

}

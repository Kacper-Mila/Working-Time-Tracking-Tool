package com.project.w3t.model.request;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RequestDateRange {

    public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
    }

}

package com.project.w3t.model.request;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RequestDateRange {

    public static final String INVALID_DATE_RANGE = "Invalid date range.";
    public static final int DAYS_TO_ADD = 1;

    public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new BadRequestException(INVALID_DATE_RANGE);
        }
        return startDate.datesUntil(endDate.plusDays(DAYS_TO_ADD)).collect(Collectors.toList());
    }

}

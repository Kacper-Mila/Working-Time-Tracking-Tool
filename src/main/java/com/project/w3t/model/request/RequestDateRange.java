package com.project.w3t.model.request;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RequestDateRange {

    public static List<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("Invalid date range.");
        }
        return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
    }

}

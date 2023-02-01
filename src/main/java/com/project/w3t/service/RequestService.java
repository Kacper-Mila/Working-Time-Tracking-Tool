package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDateRange;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RequestService {
    private final static int COMMENT_MAX_LENGTH = 250;
    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> getAllRequests() {
        List<Request> tmpRequestList = requestRepository.findAll();

        if (tmpRequestList.isEmpty()) throw new BadRequestException("Could not find any requests.");
        return tmpRequestList;
    }

    public void addRequest(Request request) {
        if (!isRequestValid(request)) {
            throw new BadRequestException("Invalid date range.");
        }
        if (!isCommentLengthValid(request.getComment())) {
            throw new BadRequestException("Comment is too long.");
        }
        requestRepository.save(request);
    }

    private boolean isRequestValid(Request request) {
        List<Request> userRequestsFilteredByType = requestRepository.findAllByType(request.getType());
        return (isDateRangeValid(request) && isDateRangeAvailable(userRequestsFilteredByType, request.getRequestDateRange()));
    }

    private boolean isDateRangeValid(Request request) {
        return (request.getStartDate() != null && request.getEndDate() != null
                && isStartDateBeforeEndDate(request.getStartDate(), request.getEndDate()));
    }

    private boolean isDateRangeAvailable(List<Request> requests, List<LocalDate> dateRange) {
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date) || date.isBefore(LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private boolean isStartDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate);
    }

    private boolean checkDateAvailability(List<Request> requests, LocalDate date) {
        return requests.stream().noneMatch(req -> req.getRequestDateRange().contains(date));
    }

    private boolean isCommentLengthValid(String comment) {
        return comment != null && comment.length() <= COMMENT_MAX_LENGTH;
    }

    @Transactional
    public void updateRequest(Long id, RequestDto requestDto) {
        Optional<Request> request = getRequestByRequestId(id);
        Request requestToUpdate = request.get();
        List<LocalDate> dateRange = RequestDateRange.getDateRange(requestDto.getStartDate(), requestDto.getEndDate());
        String comment = requestDto.getComment();

        if (!isDateRangeAvailable(getRequestsToCheckDateRange(requestToUpdate), dateRange)) {
            throw new BadRequestException("Invalid date range.");
        }

        if (!isCommentLengthValid(comment)) {
            throw new BadRequestException("Comment is too long.");
        }

        updateRequestParameters(requestDto, requestToUpdate);

        if (!isDateRangeValid(requestToUpdate)) {
            throw new BadRequestException("Invalid date range.");
        }

        if (!isDateRangeAvailable(getRequestsToCheckDateRange(requestToUpdate), dateRange)) {
            throw new BadRequestException("Invalid date range.");
        }

        requestRepository.save(requestToUpdate);
    }

    private void updateRequestParameters(RequestDto requestDto, Request requestToUpdate) {
        requestToUpdate.setType(requestDto.getType());
        requestToUpdate.setStartDate(requestDto.getStartDate());
        requestToUpdate.setEndDate(requestDto.getEndDate());
        requestToUpdate.setComment(requestDto.getComment());
        requestToUpdate.setRegistrationDate(LocalDate.now());
        requestToUpdate.setStatus(RequestStatus.PENDING);
    }


    private List<Request> getRequestsToCheckDateRange(Request request) {
        return getAllRequestsByType(request.getType()).stream()
                .filter(Predicate.not(req -> req.getRequestId().equals(request.getRequestId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new BadRequestException("Request with this id does not exists.");
        }
        requestRepository.deleteById(requestId);
    }

    public List<Request> getAllRequestsByType(RequestType requestType) {
        return requestRepository.findAllByType(requestType);
    }


    public Optional<Request> getRequestByRequestId(Long id) {
        if (!requestRepository.existsById(id)) throw new BadRequestException("Request with this id does not exists.");

        return requestRepository.findById(id);
    }
}

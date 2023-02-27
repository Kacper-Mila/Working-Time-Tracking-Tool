package com.project.w3t.service;

import com.project.w3t.exceptions.BadRequest400.BadRequestException;
import com.project.w3t.exceptions.NotFound404.NotFoundException;
import com.project.w3t.model.request.Request;
import com.project.w3t.model.request.RequestDateRange;
import com.project.w3t.model.request.RequestDto;
import com.project.w3t.model.request.RequestStatus;
import com.project.w3t.model.request.RequestType;
import com.project.w3t.model.user.User;
import com.project.w3t.repository.RequestRepository;
import com.project.w3t.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserRepository userRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public List<Request> getAllRequests() {
        if (requestRepository.findAll().isEmpty()) {
            throw new BadRequestException("Could not find any requests.");
        }
        return requestRepository.findAll();
    }

    public void addRequest(Request request) {
        Optional<String> userId = Optional.ofNullable(request.getOwnerId());
        if (userId.isEmpty() || userId.get().equals(""))
            throw new BadRequestException("Unable to process request - owner's id is invalid.");

        if (!isRequestTypeValid(request.getType())) {
            throw new BadRequestException("Invalid request type.");
        }

        if (!isRequestDateRangeValid(request, userId.get())) {
            throw new BadRequestException("Invalid date range.");
        }
        if (!isCommentLengthValid(request.getComment())) {
            throw new BadRequestException("Comment is not valid.");
        }
        Optional<User> userToSet = Optional.ofNullable(userRepository.findByUserId(userId.get()));
        if (userToSet.isEmpty()) throw new NotFoundException("Unable to process request - user does not exist.");
        request.setUser(userToSet.get());
        reduceUserAvailableHolidays(request);
        requestRepository.save(request);
    }

    private boolean isRequestDateRangeValid(Request request, String userId) {
        List<Request> userRequestsFilteredByType = requestRepository.findAllByOwnerId(userId)
                .stream()
                .filter(r -> r.getType().equals(request.getType()))
                .collect(Collectors.toList());
        return (isDateRangeValid(request) && isDateRangeAvailable(userRequestsFilteredByType, request.getRequestDateRange()));
    }

    private boolean isDateRangeValid(Request request) {
        return (request.getStartDate() != null && request.getEndDate() != null);
    }

    private boolean isDateRangeAvailable(List<Request> requests, List<LocalDate> dateRange) {
        for (LocalDate date : dateRange) {
            if (!checkDateAvailability(requests, date) || date.isBefore(LocalDate.now())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDateAvailability(List<Request> requests, LocalDate date) {
        return requests.stream().noneMatch(req -> req.getRequestDateRange().contains(date));
    }

    private boolean isCommentLengthValid(String comment) {
        return comment != null && comment.length() <= COMMENT_MAX_LENGTH;
    }

    private boolean isRequestTypeValid(RequestType requestType) {
        return requestType != null;
    }

    private void reduceUserAvailableHolidays(Request request) {
        if (request.getType().equals(RequestType.HOLIDAY)) {
            User user = request.getUser();
            if (user.getHolidays() < request.getRequestDateRange().size()) {
                throw new BadRequestException("User does not have enough available holidays");
            }
            user.setHolidays(user.getHolidays() - request.getRequestDateRange().size());
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateRequest(String userId, Long id, RequestDto requestDto) {
        Request requestToUpdate = getRequestByRequestId(id).get();
        List<LocalDate> dateRange = RequestDateRange.getDateRange(requestDto.getStartDate(), requestDto.getEndDate());
        String comment = requestDto.getComment();

        if (!isRequestTypeValid(requestDto.getType())) {
            throw new BadRequestException("Invalid request type.");
        }
        if (!isDateRangeAvailable(getRequestsToCheckDateRange(userId, requestToUpdate), dateRange)) {
            throw new BadRequestException("Invalid date range.");
        }

        if (!isCommentLengthValid(comment)) {
            throw new BadRequestException("Comment is too long.");
        }

        updateRequestParameters(requestDto, requestToUpdate);

//        if (!isDateRangeValid(requestToUpdate)) {
//            throw new BadRequestException("Invalid date range.");
//        }

        if (!isDateRangeAvailable(getRequestsToCheckDateRange(userId, requestToUpdate), dateRange)) {
            throw new BadRequestException("Invalid date range.");
        }
        reduceUserAvailableHolidays(requestToUpdate);
        reduceUserAvailableHolidays(requestToUpdate);
        requestRepository.save(requestToUpdate);
    }

    //    TODO implement update only when NonNull and not empty string ""
    private void updateRequestParameters(RequestDto requestDto, Request requestToUpdate) {
        requestToUpdate.setType(requestDto.getType());
        requestToUpdate.setStartDate(requestDto.getStartDate());
        requestToUpdate.setEndDate(requestDto.getEndDate());
        requestToUpdate.setComment(requestDto.getComment());
        requestToUpdate.setRegistrationDate(LocalDate.now());
        requestToUpdate.setStatus(RequestStatus.PENDING);
    }


    private List<Request> getRequestsToCheckDateRange(String userId, Request request) {
        return getRequestsByUserId(userId).stream()
                .filter(r -> r.getType().equals(request.getType()))
                .filter(Predicate.not(req -> req.getId().equals(request.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRequest(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new NotFoundException("Request with this id does not exists.");
        }
        requestRepository.deleteById(requestId);
    }

    public List<Request> getAllRequestsByType(RequestType requestType) {
        if (requestRepository.findAllByType(requestType).isEmpty())
            throw new NotFoundException("Could not find any requests by this type.");
        return requestRepository.findAllByType(requestType);
    }


    public Optional<Request> getRequestByRequestId(Long id) {
        if (!requestRepository.existsById(id)) throw new NotFoundException("Request with this id does not exists.");
        return requestRepository.findById(id);
    }

    public List<Request> getRequestsByUserId(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new NotFoundException("User with this id does not exist.");
        }
        return requestRepository.getRequestsByUserUserId(userId);
    }

    public List<Request> getEmployeesRequestsByManagerId(String managerId) {
        if (!userRepository.existsByManagerId(managerId)) {
            throw new NotFoundException("Manager with this id does not exist.");
        }
        return requestRepository.getEmployeesRequestsByManagerIdQuery(managerId)
                .stream()
                .filter(r -> r.getStatus().equals(RequestStatus.PENDING))
                .collect(Collectors.toList());
    }
}
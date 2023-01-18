package com.project.w3t.repository;

import com.project.w3t.model.Request;
import com.project.w3t.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestStorage {

    private List<Request> userRequestList = new ArrayList<>();

    public void addRequest(Request request) {
        userRequestList.add(request);
    }

    public List<Request> getUserRequestListByType(Type requestType) {

//        Type requestType = null;
//        for (Type value : Type.values()) {
//            if (value.toString().equals(requestTypeString)) {
//                requestType = value;
//            }
//        }


        return switch (requestType) {
            case HOLIDAY -> userRequestList.stream().filter(request -> request.getType().equals(Type.HOLIDAY)).collect(Collectors.toList());
            case OVERTIME -> userRequestList.stream().filter(request -> request.getType().equals(Type.OVERTIME)).collect(Collectors.toList());
            case REMOTE ->userRequestList.stream().filter(request -> request.getType().equals(Type.REMOTE)).collect(Collectors.toList());
        };
    }
}

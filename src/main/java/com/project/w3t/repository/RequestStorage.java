package com.project.w3t.repository;

import com.project.w3t.model.Request;
import com.project.w3t.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestStorage implements RequestRepository{

    private List<Request> userRequestList = new ArrayList<>();

    public void addRequest(Request request) {
        userRequestList.add(request);
    }

    public Request getRequestById(Long requestId){
        return userRequestList.get(requestId.intValue());
    }
}

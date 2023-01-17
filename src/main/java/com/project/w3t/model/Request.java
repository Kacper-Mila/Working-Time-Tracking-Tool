package com.project.w3t.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Request {

    private Long requestId;
    private String ownerId;
    private Type type;
    private String comment;
    private Date registrationDate;
    private Date startDate;
    private Date endDate;
    private Date approvalDate;
    private Status status;
}

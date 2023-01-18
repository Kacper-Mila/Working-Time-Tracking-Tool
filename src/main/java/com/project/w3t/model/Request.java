package com.project.w3t.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId = 1L;

    private String ownerId;
    private Type type;
    private String comment;
    private Date registrationDate;
    private Date startDate;
    private Date endDate;
    private Date approvalDate;
    private Status status;

}

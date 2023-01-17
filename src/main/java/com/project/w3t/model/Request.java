package com.project.w3t.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class Request {

    private Long id;
    private Long ownerId;
    private Type type;
    private Status status;
    private String comment;
//    data złożenia, przedział czasu, data akceptacji/odrzucenia
}

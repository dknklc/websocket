package com.dekankilic.websocket.model;

import com.dekankilic.websocket.model.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class User {
    @Id
    private String nickName;
    private String fullName;
    private Status status;
}

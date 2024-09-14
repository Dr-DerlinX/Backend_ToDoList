package com.core.to_do_list.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public class ErrorMessege {

    private HttpStatus httpStatus;
    private String messege;

    public ErrorMessege(HttpStatus httpStatus, String messege) {
        this.httpStatus = httpStatus;
        this.messege = messege;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }
}

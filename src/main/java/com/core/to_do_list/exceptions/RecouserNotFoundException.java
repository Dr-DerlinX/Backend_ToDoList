package com.core.to_do_list.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecouserNotFoundException extends Exception{

    public RecouserNotFoundException(String message) {
        super(message);
    }

}

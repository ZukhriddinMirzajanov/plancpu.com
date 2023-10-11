package com.plancpu.backend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class AlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String message;
    public AlreadyExistsException( String message) {
        this.message = message;
    }
}

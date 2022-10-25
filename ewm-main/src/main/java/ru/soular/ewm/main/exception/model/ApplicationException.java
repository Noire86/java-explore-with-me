package ru.soular.ewm.main.exception.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    @Getter
    private final HttpStatus code;
    @Getter
    private final ExceptionResponse response;

    public ApplicationException(String message, HttpStatus code) {
        super(message);
        this.code = code;
        response = null;
    }

    public ApplicationException(ExceptionResponse response) {
        super(response.getMessage());
        this.response = response;
        this.code = HttpStatus.valueOf(response.getStatus());
    }
}

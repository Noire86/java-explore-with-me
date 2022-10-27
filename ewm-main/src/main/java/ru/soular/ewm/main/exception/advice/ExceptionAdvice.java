package ru.soular.ewm.main.exception.advice;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.soular.ewm.main.exception.model.ApplicationException;
import ru.soular.ewm.main.exception.model.ExceptionResponse;
import ru.soular.ewm.main.util.Constants;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    @NonNull
    public ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, @Nullable Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();

        if (ex instanceof BindException) {
            ((BindException) ex).getFieldErrors().forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));
            ((BindException) ex).getGlobalErrors().forEach(err -> errors.add(err.getObjectName() + ": " + err.getDefaultMessage()));
        }

        ExceptionResponse response = ExceptionResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .reason("For the requested operation the conditions are not met.")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(Constants.FORMATTER))
                .build();

        log.warn(String.format("%s is thrown: %s", ex.getClass().getSimpleName(), ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.CONFLICT.name())
                .reason("Integrity constraint has been violated")
                .message(ex.getSQLException().getMessage())
                .timestamp(LocalDateTime.now().format(Constants.FORMATTER))
                .errors(new ArrayList<>())
                .build();

        log.warn(String.format("%s is thrown: %s", ex.getClass().getSimpleName(), ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .reason("The required object was not found.")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(Constants.FORMATTER))
                .errors(new ArrayList<>())
                .build();

        log.warn(String.format("%s is thrown: %s", ex.getClass().getSimpleName(), ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        ExceptionResponse response = ex.getResponse() == null ? ExceptionResponse.builder()
                .status(ex.getCode().name())
                .reason("For the requested operation the conditions are not met.")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().format(Constants.FORMATTER))
                .errors(new ArrayList<>())
                .build() : ex.getResponse();

        log.warn(String.format("%s is thrown: %s", ex.getClass().getSimpleName(), ex.getMessage()));
        return new ResponseEntity<>(response, ex.getCode());
    }
}

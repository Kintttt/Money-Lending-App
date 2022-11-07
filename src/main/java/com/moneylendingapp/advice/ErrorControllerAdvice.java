package com.moneylendingapp.advice;

import com.moneylendingapp.exceptions.BadRequestException;
import com.moneylendingapp.exceptions.UserNotFoundException;
import liquibase.repackaged.org.apache.commons.lang3.NotImplementedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ErrorControllerAdvice {

    private final Clock clock;

    private ApiResponseEnvelope buildErrorResponse(Object error) {
        return ApiResponseEnvelope.builder()
                .successStatus(false)
                .responseDate(LocalDateTime.now(clock))
                .errorMessage(Collections.singletonList(error))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponseEnvelope handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> response = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.add(fieldName +": " + errorMessage);
        });
        log.error(ex.getMessage());
        return buildErrorResponse(response);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseEnvelope handleIllegalStateException(IllegalStateException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = NotImplementedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseEnvelope handleNotImplementedException(NotImplementedException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }


    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponseEnvelope handleBadRequest(BadRequestException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseEnvelope handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseEnvelope handleException(Exception ex) {
        log.error("ERROR OCCURRED! " + ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }
}

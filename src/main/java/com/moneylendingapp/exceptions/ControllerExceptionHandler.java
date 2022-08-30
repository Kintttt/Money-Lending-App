package com.moneylendingapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Map<Object, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<Object, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalStateException(IllegalStateException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(UnexpectedTypeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String handleUnexpectedTypeException(UnexpectedTypeException ex, HttpServletRequest request) {
//        return ex.getMessage();
//    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handBadRequest(BadRequestException ex) {
        log.error(ex.getMessage());
        return ex.getMessage();
    }

}

package com.moneylendingapp.advice;

import com.moneylendingapp.exceptions.BadRequestException;
import jdk.jshell.spi.ExecutionControl;
import liquibase.repackaged.org.apache.commons.lang3.NotImplementedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
@Slf4j
public class ApiAdviceHandler implements ResponseBodyAdvice<Object> {

    private ApiResponseEnvelope buildErrorResponse(Object error) {
        return ApiResponseEnvelope.builder()
                .status(false)
                .timeStamp(LocalDateTime.now())
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
    public ApiResponseEnvelope handBadRequest(BadRequestException ex) {
        log.error(ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseEnvelope handleException(Exception ex) {
        log.error("ERROR OCCURRED! " + ex.getMessage());
        return buildErrorResponse(ex.getMessage());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body instanceof ApiResponseEnvelope ? body : ApiResponseEnvelope.builder()
                .result(body)
                .timeStamp(LocalDateTime.now())
                .status(true)
                .build();
    }
}

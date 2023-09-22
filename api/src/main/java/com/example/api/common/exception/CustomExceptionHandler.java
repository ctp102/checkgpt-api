package com.example.api.common.exception;

import com.example.core.common.enums.ErrorCodes;
import com.example.core.common.exception.*;
import com.example.core.common.response.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.api")
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public CustomResponse bindException(BindException e) {
        log.error("[BindException 발생] {}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBadRequestException.class)
    public CustomResponse badRequestException(HttpServletRequest request, CustomBadRequestException e) {
        log.error("[CustomBadRequestException 발생] Request URI: {}", request.getRequestURI(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public CustomResponse unAuthorizedException(HttpServletRequest request, CustomUnauthorizedException e) {
        log.error("[CustomUnauthorizedException 발생] Request URI: {}", request.getRequestURI(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(CustomAccessDeniedException.class)
    public CustomResponse accessDeniedException(HttpServletRequest request, CustomAccessDeniedException e) {
        log.error("[CustomAccessDeniedException 발생] Request URI: {}", request.getRequestURI(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public CustomResponse notFoundException(HttpServletRequest request, CustomNotFoundException e) {
        log.error("[CustomNotFoundException 발생] Request URI: {}", request.getRequestURI(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomConflictException.class)
    public CustomResponse conflictException(HttpServletRequest request, CustomConflictException e) {
        log.error("[conflictException 발생] Request URI: {}", request.getRequestURI(), e);
        return getCustomResponse(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)
    public CustomResponse exception(HttpServletRequest request, CustomException e) {
        log.error("[Exception 발생] Request URI: {}", request.getRequestURI(), e);
        return new CustomResponse.Builder(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    }

    private CustomResponse getCustomResponse(BindException e) {
        ErrorCodes errorCodes = ErrorCodes.findByMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new CustomResponse.Builder(errorCodes).build();
    }

    private CustomResponse getCustomResponse(Exception e) {
        ErrorCodes errorCodes = ErrorCodes.findByMessage(e.getMessage());
        return new CustomResponse.Builder(errorCodes).build();
    }

}

package io.hexbit.api.exception;

import io.hexbit.core.common.enums.ErrorCodes;
import io.hexbit.core.common.exception.*;
import io.hexbit.core.common.response.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "io.hexbit.api")
public class CustomExceptionHandler {


    /**
     * QueryParam 공백 문자 제거
     * QueryParam에 적용되는 어노테이션이기 때문에 Post나 Put 환경에는 적용되지 않는다.
     */
    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor ste = new StringTrimmerEditor(true);    // emptyAsNull: true (빈문자열은 null로 파싱함)
        dataBinder.registerCustomEditor(String.class, ste);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BindException.class)
//    public CustomResponse bindException(BindException e) {
//        log.error("[BindException 발생] {}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);
//        return getCustomResponse(e);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException 발생] {}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), e);
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

package com.viettel.vtskit.common.exception;

import com.viettel.vtskit.common.rest.ErrorDTO;
import com.viettel.vtskit.common.utils.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<ErrorDTO> handleAccessDeniedException() {
        return RestUtils.responseStatus(HttpStatus.FORBIDDEN, new ErrorDTO(HttpStatus.FORBIDDEN.value(), "Access Denied"));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorDTO> handleCommonException(Exception ex) {
        return RestUtils.responseServerError();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorDTO> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errorList.add(error.getDefaultMessage());
        });
        String errorMessage = errorList.stream().collect(Collectors.joining("; "));
        return RestUtils.responseStatus(HttpStatus.BAD_REQUEST, new ErrorDTO(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorList = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            errorList.add(error.getMessage());
        });
        String errorMessage = errorList.stream().collect(Collectors.joining("; "));
        return RestUtils.responseStatus(HttpStatus.BAD_REQUEST, new ErrorDTO(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler({BindException.class, MissingServletRequestPartException.class,
            MissingServletRequestParameterException.class, MissingRequestHeaderException.class})
    protected ResponseEntity<ErrorDTO> handleMissingPartException(Exception ex) {
        return RestUtils.responseStatus(HttpStatus.BAD_REQUEST, new ErrorDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

}

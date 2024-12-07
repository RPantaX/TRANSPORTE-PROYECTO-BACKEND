package com.prueba.sintad.middleware;

import com.prueba.sintad.aggregates.constants.Constants;
import com.prueba.sintad.aggregates.exceptions.SintadAppBadRequestException;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotAcceptableException;
import com.prueba.sintad.aggregates.exceptions.SintadAppNotFoundException;
import com.prueba.sintad.aggregates.response.error.ResponseError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestMiddleware {
    @ExceptionHandler({SintadAppBadRequestException.class})
    private ResponseEntity<ResponseError<String>> appExceptionBadRequest(SintadAppBadRequestException f, HttpServletRequest request){
        ResponseError<String> responseError = new ResponseError<>(Constants.STATUS_BAD_REQUEST, f.getMessage(), Constants.getTimestamp(), request.getRequestURI());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SintadAppNotFoundException.class})
    private ResponseEntity <ResponseError<String>> appExceptionNotFound(SintadAppNotFoundException f, HttpServletRequest request){
        ResponseError<String> responseError = new ResponseError<>(Constants.STATUS_NOT_FOUND, f.getMessage(), Constants.getTimestamp(), request.getRequestURI());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({SintadAppNotAcceptableException.class})
    private ResponseEntity <ResponseError<String>> appExceptionNotAcceptable(SintadAppNotAcceptableException f, HttpServletRequest request){
        ResponseError<String> responseError = new ResponseError<>(Constants.STATUS_NOT_ACCEPTABLE, f.getMessage(), Constants.getTimestamp(),request.getRequestURI());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError<Map<String, String>>> handleInvalidArguments(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ResponseError<Map<String, String>> responseError = new ResponseError<>(Constants.STATUS_NOT_ACCEPTABLE, errors, Constants.getTimestamp(), request.getRequestURI());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<ResponseError<String>> globalException(Exception ex, HttpServletRequest request) {
        ResponseError<String> error = new ResponseError<>(Constants.STATUS_INTERNAL_SERVER_ERROR, ex.getMessage(),Constants.getTimestamp(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

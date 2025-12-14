package com.example.rosnama.Advice;

import com.example.rosnama.Api.ApiException;
import com.example.rosnama.Api.ApiResponse;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiException(ApiException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> ConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> DataIntegrityConstraintViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }


    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponse> HttpMessageNotReadableException(WebClientResponseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse> HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage()));
    }

}


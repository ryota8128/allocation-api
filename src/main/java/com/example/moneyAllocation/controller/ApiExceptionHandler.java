package com.example.moneyAllocation.controller;

<<<<<<< Updated upstream
import com.example.moneyAllocation.repository.ResourceNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, "データベースの制約に違反しました", HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, "該当するリソースが見つかりません", HttpHeaders.EMPTY, HttpStatus.NOT_FOUND, request);
    }



}
=======
public class ApiExceptionHandler {}
>>>>>>> Stashed changes

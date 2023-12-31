package com.example.moneyAllocation.controller.handler;

import com.example.moneyAllocation.exception.BudRequestException;
import com.example.moneyAllocation.exception.HealthDieException;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.exception.ResourceValidationException;
import com.example.moneyAllocation.exception.UserRegisterException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
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

    @ExceptionHandler
    public ResponseEntity<Object> handleResourceValidationException(
            ResourceValidationException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleSQLDuplicateKeyException(
            DuplicateKeyException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, "重複したデータを追加しました", HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleHealthDieException(
            HealthDieException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleUserRegisterException(
            UserRegisterException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleBudRequestException(
            BudRequestException exception, WebRequest request) {
        return super.handleExceptionInternal(exception, exception.getMessage(), HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }
}

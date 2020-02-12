package com.example.faisal.exceptions;

import com.example.faisal.dto.errors.ApiErrorResponse;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;


@Slf4j
@RestControllerAdvice
public class RestExceptionAdvice extends AbstractExceptionHandler {

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        String message = "internal server error";
        log.error("Exception", ex);
        return super.handleExceptionInternally(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return super.handleConstraintViolation(ex, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return super.handleMethodArgumentTypeMismatch(ex, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class, IllegalArgumentException.class, UnrecognizedPropertyException.class})
    public final ResponseEntity<Object> handleBadRequestException(final Exception ex, final WebRequest request) {
        return super.handleExceptionInternally(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class, NotFoundException.class})
    public final ResponseEntity<Object> handleResourceNotFoundException(final Exception ex, final WebRequest request) {
        return super.handleExceptionInternally(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public final ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> handleException(Exception exception, final HttpStatus httpStatus) {
        ApiErrorResponse apiErrorResponse;
        apiErrorResponse = new ApiErrorResponse(httpStatus.value(), exception.getMessage());
        return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), httpStatus);
    }
}

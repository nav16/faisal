package com.example.faisal.exceptions;


import com.example.faisal.dto.errors.ApiErrorResponse;
import com.example.faisal.dto.errors.ApiErrorsField;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

public abstract class AbstractExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorMap = new HashMap();
        Iterator iterator = ex.getBindingResult().getFieldErrors().iterator();

        while(iterator.hasNext()) {
            FieldError error = (FieldError)iterator.next();
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        iterator = ex.getBindingResult().getGlobalErrors().iterator();

        while(iterator.hasNext()) {
            ObjectError error = (ObjectError)iterator.next();
            errorMap.put(error.getObjectName(), error.getDefaultMessage());
        }

        String message = "Validation failed. " + errorMap.size() + " error(s)";
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message, ApiErrorsField.buildErrorFields(errorMap));
        return this.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiErrorsField> errorFields = Collections.singletonList(new ApiErrorsField(ex.getParameterName(), "parameter is missing"));
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), errorFields);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiErrorsField> errorFields = Collections.singletonList(new ApiErrorsField(ex.getRequestURL(), "No handler found for " + ex.getHttpMethod()));
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), errorFields);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiErrorsField> errorFields = Collections.singletonList(new ApiErrorsField(ex.getMethod(), "method is not supported for this request"));
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getLocalizedMessage(), errorFields);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiErrorsField> errorFields = Collections.singletonList(new ApiErrorsField("" + ex.getContentType(), "media type is not supported"));
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getLocalizedMessage(), errorFields);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), this.parseExceptionLocalizedMessage(ex));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), this.parseExceptionLocalizedMessage(ex));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Set<? extends ConstraintViolation<?>> violations = ex.getConstraintViolations();
        Map<String, String> errorMap = new HashMap();
        Iterator var5 = violations.iterator();

        while(var5.hasNext()) {
            ConstraintViolation<?> violation = (ConstraintViolation)var5.next();
            String key = violation.getPropertyPath() != null ? violation.getPropertyPath().toString() : "";
            errorMap.put(key, violation.getMessage());
        }

        String message = "Validation failed. " + errorMap.size() + " error(s)";
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message, ApiErrorsField.buildErrorFields(errorMap));
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        List<ApiErrorsField> errorFields = Collections.singletonList(new ApiErrorsField(ex.getName(), "should be of type " + ex.getRequiredType().getName()));
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), errorFields);
        return new ResponseEntity(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleExceptionInternally(int code, String message, HttpStatus httpStatus) {
        ApiErrorResponse apiError = new ApiErrorResponse(code, message);
        return new ResponseEntity(apiError, new HttpHeaders(), httpStatus);
    }

    protected String parseExceptionLocalizedMessage(Exception ex) {
        String localizedMessage = ex.getLocalizedMessage();
        if (localizedMessage == null) {
            return null;
        } else {
            int semiColonIndex = localizedMessage.indexOf(":");
            return semiColonIndex == -1 ? localizedMessage : localizedMessage.substring(0, semiColonIndex);
        }
    }
}

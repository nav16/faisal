package com.example.faisal.dto.errors;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Collections;
import java.util.List;

@JsonTypeName("error")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public final class ApiErrorResponse {
    private int status;
    private String message;
    private List<ApiErrorsField> errors;

    public ApiErrorResponse(int status, String message, List<ApiErrorsField> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(int status, String message, ApiErrorsField error) {
        this.status = status;
        this.message = message;
        this.errors = error != null ? Collections.singletonList(error) : null;
    }

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.errors = null;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public List<ApiErrorsField> getErrors() {
        return this.errors;
    }
}

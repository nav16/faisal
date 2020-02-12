package com.example.faisal.dto.errors;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseStrategy.class)
public final class ApiErrorsField {
    private String field;
    private String message;

    public ApiErrorsField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public static List<ApiErrorsField> buildErrorFields(Map<String, String> errorMap) {
        List<ApiErrorsField> apiErrorsFields = new ArrayList();
        errorMap.forEach((key, value) -> {
            apiErrorsFields.add(new ApiErrorsField(key, value));
        });
        return apiErrorsFields;
    }

    public String getField() {
        return this.field;
    }

    public String getMessage() {
        return this.message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

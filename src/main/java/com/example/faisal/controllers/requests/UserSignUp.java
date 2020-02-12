package com.example.faisal.controllers.requests;

import com.example.faisal.models.enums.UsersType;
import com.example.faisal.utils.validators.CheckEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignUp {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    @CheckEnum(UsersType.class)
    private String userType;

}

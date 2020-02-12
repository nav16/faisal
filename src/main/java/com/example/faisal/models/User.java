package com.example.faisal.models;

import com.example.faisal.models.enums.UsersType;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
@Data
public class User extends Base {

    @NotNull
    private String name;

    @Email
    @Column(unique = true)
    private String email;

}

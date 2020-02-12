package com.example.faisal.services;

import com.example.faisal.controllers.requests.UserSignUp;
import com.example.faisal.models.User;

public interface UserService {
    User create (UserSignUp userSignUp);

    User findUser (Long userId);
}

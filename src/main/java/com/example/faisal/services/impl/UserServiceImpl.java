package com.example.faisal.services.impl;

import com.example.faisal.controllers.requests.UserSignUp;
import com.example.faisal.models.User;
import com.example.faisal.models.enums.UsersType;
import com.example.faisal.repositories.UserRepository;
import com.example.faisal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserSignUp userSignUp) {
        User user = new User();
        user.setName(userSignUp.getName());
        user.setEmail(userSignUp.getEmail());
        user.setUserType(UsersType.ADMIN);
        return userRepository.save(user);
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.getOne(userId);
    }
}

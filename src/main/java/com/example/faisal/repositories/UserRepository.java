package com.example.faisal.repositories;

import com.example.faisal.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Base<User, Long> {
    // Add custom methods to be included in this repo

}

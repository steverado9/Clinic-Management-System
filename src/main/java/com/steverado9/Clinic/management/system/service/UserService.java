package com.steverado9.Clinic.management.system.service;

import com.steverado9.Clinic.management.system.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);

    User saveUser(User user);

    Optional<User> findByEmail(String email);
}

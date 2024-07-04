package com.codegym.service;

import com.codegym.model.User;
import com.codegym.model.dto.UpdatePasswordRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User getUserById(Long userId);
    void save(User user);
    void remove(Long id);
    User findByUserName(String username);
    String generateNewPassword();
    boolean changePassword(UpdatePasswordRequest request);

}
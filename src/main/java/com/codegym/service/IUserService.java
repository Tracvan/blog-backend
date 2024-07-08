package com.codegym.service;

import com.codegym.model.InfoUser;
import com.codegym.model.User;
import com.codegym.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    InfoUser getInfoUser(Long id);

    List<User> getUsers();
    User getUserById(Long userId);
    void save(User user);
    void remove(Long id);
    User findByUserName(String username);
    String generateNewPassword();
    List<UserDTO> getAllUsers();
    void lockUser(Long id);
    void unlockUser(Long id);


}
package com.codegym.service;

import com.codegym.model.User;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.model.dto.UserProfileUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User getUserById(Long userId);
    void save(User user);
    void remove(Long id);
    User findByUserName(String username);
    String generateNewPassword();
    List<UserDTO> getAllUsers();
    void lockUser(Long id);
    void unlockUser(Long id);

    UserDetailDTO getUserDetailById(Long id);

    UserProfileUpdateDTO getUserProfileById(Long id);

    void updateUserProfile(Long id, UserProfileUpdateDTO userProfileUpdateDTO);

}
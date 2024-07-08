
package com.codegym.service.imp;

import com.codegym.model.InfoUser;
import com.codegym.model.User;

import com.codegym.model.dto.UserDTO;
import com.codegym.repository.IUserRepository;
import com.codegym.repository.InfoUserRepository;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private InfoUserRepository infoUserRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public String generateNewPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        int length = random.nextInt(3) + 6;

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return infoUserRepository.findAllUsers();
    }

    @Override
    public void lockUser(Long id) {
        infoUserRepository.lockUserById(id);
    }

    @Override
    public void unlockUser(Long id) {
        infoUserRepository.unlockUserById(id);
    }

    @Override
    public void remove(Long id) {
        infoUserRepository.deleteUserById(id);
    }
    @Override
    public InfoUser getInfoUser(Long id){
       return infoUserRepository.getReferenceById(id);

    }
}

package com.codegym.service.imp;

import com.codegym.model.InfoUser;
import com.codegym.model.User;
import com.codegym.model.dto.UpdatePasswordRequest;

import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.model.dto.UserProfileUpdateDTO;
import com.codegym.repository.IUserRepository;
import com.codegym.repository.InfoUserRepository;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final PasswordEncoder passwordEncoder;


    @Autowired
    IUserRepository userRepository;

    @Autowired
    private InfoUserRepository infoUserRepository;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public InfoUser getInfoUser(Long id) {
        return null;
    }

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
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public String generateNewPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        // Chọn độ dài ngẫu nhiên từ 6 đến 8
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
    public UserDetailDTO getUserDetailById(Long id) {
        return infoUserRepository.findUserDetailById(id);
    }

    @Override
    public List<User> searchUsers(String query) {
        return null;
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
    public boolean changePassword(UpdatePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (username != null){
            User user = userRepository.findUserByUsername(username);
            if(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserProfileUpdateDTO getUserProfileById(Long id) {return infoUserRepository.findInfoUserById(id);}

    @Override
    public void updateUserProfile(Long id, UserProfileUpdateDTO userProfileUpdateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        InfoUser infoUser = infoUserRepository.findById(id).orElseThrow(() -> new RuntimeException("InfoUser not found"));

        user.setEmail(userProfileUpdateDTO.getEmail());
        userRepository.save(user);

        infoUser.setAvatar(userProfileUpdateDTO.getAvatar());
        infoUser.setFullName(userProfileUpdateDTO.getFullName());
        infoUser.setAddress(userProfileUpdateDTO.getAddress());
        infoUser.setPhonenumber(userProfileUpdateDTO.getPhoneNumber());
        infoUserRepository.save(infoUser);
    }

}




package com.codegym.controller;

import com.codegym.model.dto.UserDTO;

import com.codegym.repository.InfoUserRepository;
import com.codegym.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/info")
public class InfoUserController {

    @Autowired
    private InfoUserRepository infoUserRepository;

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> infoUserRepository.findById(user.getId())
                        .map(infoUser -> new UserDTO(
                                user.getId(),
                                user.getUsername(),
                                infoUser.getCreatedAt(),
                                infoUser.getAvatar(),
                                infoUser.getFullName(),
                                infoUser.getStatus()
                        ))
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/user/{id}")
//    public ResponseEntity<?> updateUserInfo(@PathVariable Long id, @Valid @RequestBody UpdateUserInfoRequest updateUserInfoRequest) {
//        return userRepository.findById(id)
//                .map(user -> infoUserRepository.findById(user.getId())
//                        .map(infoUser -> {
//                            user.setUsername(updateUserInfoRequest.getUsername());
//                            user.setEmail(updateUserInfoRequest.getEmail());
//                            infoUser.setFullName(updateUserInfoRequest.getFullName());
//                            infoUserRepository.save(infoUser);
//                            userRepository.save(user);
//                            return ResponseEntity.ok(new UpdateUserInfoResponse("User information updated successfully"));
//                        })
//                        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UpdateUserInfoResponse("Failed to update user information"))))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
}

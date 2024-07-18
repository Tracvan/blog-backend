package com.codegym.controller;

import com.codegym.model.dto.UserDTO;
import com.codegym.repository.InfoUserRepository;
import com.codegym.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                infoUser.getUser().getDate(),
                                infoUser.getAvatar(),
                                infoUser.getFullName(),
                                infoUser.getStatus()
                        ))
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

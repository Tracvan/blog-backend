package com.codegym.controller;

import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/users/lock/{id}")
    public ResponseEntity<String> lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.ok("User locked successfully");
    }


    @PutMapping("/users/unlock/{id}")
    public ResponseEntity<String> unlockUser(@PathVariable Long id) {
        userService.unlockUser(id);
        return ResponseEntity.ok("User unlocked successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDetailDTO> getUserDetailById(@PathVariable Long id) {
        UserDetailDTO userDetail = userService.getUserDetailById(id);
        return ResponseEntity.ok(userDetail);
    }
}

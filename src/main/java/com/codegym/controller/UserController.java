package com.codegym.controller;

import com.codegym.model.Email;
import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.model.dto.UpdatePasswordRequest;
import com.codegym.payload.request.RegisterRequest;
import com.codegym.payload.response.RegisterResponse;
import com.codegym.repository.IUserRepository;
import com.codegym.repository.RoleRepository;
import com.codegym.security.JwtTokenProvider;
import com.codegym.service.IUserService;
import com.codegym.service.imp.EmailService;
import jakarta.validation.Valid;
import org.eclipse.angus.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(new RegisterResponse("Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new RegisterResponse("Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));

        userService.save(user);

        return ResponseEntity.ok(new RegisterResponse("User registered successfully"));
    }

    @GetMapping("users/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("users/changepw/{username}")
    public ResponseEntity<?> changePassword(@PathVariable("username") String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            errorResponse.put("message", "The username '" + username + "' does not exist in our database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            String usname = user.getUsername();
            String userEmail = user.getEmail();
            String newPassword = userService.generateNewPassword();
            String encodedPassword = passwordEncoder.encode(newPassword);
            User changedPasswordUser = new User(user.getId(), usname, userEmail, encodedPassword, user.getRoles());
            userRepository.save(changedPasswordUser);
            Email email = new Email(user.getEmail(),
                    "Your New Password for BLOG ",
                    "Dear Mr/Ms <b> " + user.getUsername() + "</b>" +
                            "\n" +
                            "We're excited to welcome you to BLOG! To ensure your account is secure, we've generated a new password for you:" + "\n" +
                            "\n" +
                            "New Password: " + newPassword + "\n" +
                            "\n" +
                            "We highly recommend changing this password to something unique and memorable to you. You can easily do this by following these steps:\n" +
                            "\n" +
                            "1. Log in to your account using your email address and the new password provided above.\n" +
                            "2. Once logged in, navigate to your account settings.\n" +
                            "3. Look for the \"Change Password\" option and follow the prompts to set a new password of your choice." +
                            "\n" +
                            "Please find below a summary of your booking:\n" +
                            "\n" +
                            "Important Security Tips:\n" +
                            "\n" +
                            "Never share your password with anyone, not even us.\n" +
                            "Use a strong password that includes a combination of uppercase and lowercase letters, numbers, and symbols.\n" +
                            "Avoid using the same password for multiple accounts.\n" +
                            "Change your password regularly for added security.\n" +
                            "If you have any trouble logging in or changing your password, please don't hesitate to contact our support team at [Support Email Address] or by phone at [Support Phone Number]. We're here to help 24/7.\n" +
                            "\n" +
                            "Welcome to the BLOG community!\n" +
                            "\n" +
                            "Sincerely,\n" +
                            "\n" +
                            "The BLOG Team\n" +
                            "\n" +
                            "P.S. We'd love to hear from you! If you have any feedback or suggestions, please feel free to reply to this email or reach out to us on social media");

            emailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getText());
            return ResponseEntity.ok(encodedPassword);
        }
    }
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request){
        boolean isChanged = userService.changePassword(request);
        if(isChanged){
             return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("failed to change password");
        }
    }
}

package com.codegym.controller;

import com.codegym.model.dto.UserDTO;
import com.codegym.model.Email;
import com.codegym.model.Role;
import com.codegym.model.User;
import com.codegym.model.dto.UserProfileUpdateDTO;
import com.codegym.model.dto.UpdatePasswordRequest;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.payload.request.RegisterRequest;
import com.codegym.payload.response.RegisterResponse;
import com.codegym.repository.IUserRepository;
import com.codegym.repository.RoleRepository;
import com.codegym.security.JwtTokenProvider;
import com.codegym.service.IUserService;
import com.codegym.service.InfoUserService;
import com.codegym.service.imp.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


import java.util.HashMap;
import java.util.Map;



@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
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

    @Autowired
    InfoUserService infoUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>(new RegisterResponse("Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(new RegisterResponse("Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        LocalDate now = LocalDate.now();
        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()),
                now
                );

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singleton(userRole));

        userService.save(user);
        Email email = new Email(user.getEmail(),
                "Welcome to the Blogosphere, " +user.getUsername() + "! ",
                "Hey " + user.getUsername() +
                        "\n" +
                        "Welcome to the blogosphere! We're thrilled to have you join our community of passionate writers and readers." +
                        "\n" +
                        "We know that starting a new blog can be exciting and a little daunting at the same time. But don't worry, we're here to help you every step of the way." +
                        "Here are a few things you can do to get started:\n" +
                        "\n" +
                        "1. Explore the blog: Take some time to browse through our posts and get a feel for the kind of content we publish. You'll find a wide variety of topics, from personal stories and travelogues to insightful essays and creative writing.\n" +
                        "2. Join the conversation: Leave comments on our posts and share your thoughts and opinions. We love hearing from our readers and fostering a vibrant and engaging community.\n" +
                        "3 .Connect with other bloggers: Follow other bloggers you enjoy reading, and reach out to them through comments or social media. Building relationships with other bloggers can be a great way to learn and grow.\n" +
                        "4. Start writing! The best way to get started is to simply start writing. Share your unique voice and perspective with the world. Don't be afraid to experiment and find your own style.\n" +
                        "We're confident that you'll find our blog to be a valuable resource and a platform to share your creativity with the world.\n" +
                        "\n" +
                        "Here are some additional resources that you may find helpful:\n" +
                        "\n" +
                        "Getting Started Guide: [link to guide]\n" +
                        "FAQ: [link to FAQ]\n" +
                        "Community Guidelines: [link to guidelines]\n" +
                        "We're so glad you're here,"+ user.getUsername() +"! We're looking forward to reading your posts and getting to know you better.\n" +
                        "\n" +
                        "Happy blogging!\n" +
                        "\n" +
                        "The BLOG Team\n" +
                        "\n" +
                        "P.S. We're also running a special welcome offer for new bloggers. Use code WELCOME to get 10% off your first blog design package.");
        emailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getText());

        return ResponseEntity.ok(new RegisterResponse("User registered successfully"));
    }


    @GetMapping("users/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username){
        User user = userService.findByUserName(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("id") Long id ) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{username}")
    public List<User> searchUsers(@PathVariable("username") String username) {
        return userService.searchUsers(username);
    }

    @GetMapping("users/getpassword/{username}")
    public ResponseEntity<?> changePassword(@PathVariable("username") String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            errorResponse.put("message", "The username '" + user.getUsername() + "' does not exist in our database.");
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
                            "New Password: " + newPassword +"\n" +
                            "\n"+
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDTO user = userService.getCurrentUser();

        if(isChanged){
            LocalDate now = LocalDate.now();
            Email email = new Email(user.getEmail(),
                    "Your BLOG's Password Has Been Changed",
                    "Hi " + user.getUsername() +
            " This email confirms that your password for your BLOG account has been successfully changed.\n" +
                            "\n" +
                            "Here are the details of your password change:\n" +
                            "\n" +
                            "Date and Time: " + now + "\n" +
                            "New Password: ******* (This is hidden for security reasons)\n" +
                            "Important Security Reminder:\n" +
                            "\n" +
                            "We recommend that you choose a strong password that is at least 8 characters long and includes a mix of upper and lowercase letters, numbers, and symbols.\n" +
                            "Do not use the same password for multiple accounts.\n" +
                            "Be sure to keep your password in a safe place and do not share it with anyone.\n" +
                            "If you did not make this change, please contact us immediately at Codegymsghn@gmail.com.\n" +
                            "\n" +
                            "We hope you continue to enjoy using BLOG.\n" +
                            "\n" +
                            "Sincerely,\n" +
                            "\n" +
                            "The BLOG Team"
                    );
            emailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getText());


            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("failed to change password");
        }
    }
    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/users/profile/{userId}")
    public ResponseEntity<UserProfileUpdateDTO> getUserProfileUpdateDTO(@PathVariable Long userId) {
        UserProfileUpdateDTO userProfileUpdateDTO = userService.getUserProfileById(userId);
        if (userProfileUpdateDTO != null) {
            return ResponseEntity.ok(userProfileUpdateDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/profile/{userId}")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long userId, @Valid @RequestBody UserProfileUpdateDTO userProfileUpdateDTO) {
        try {
            userService.updateUserProfile(userId, userProfileUpdateDTO);
            return ResponseEntity.ok(new RegisterResponse("User profile updated successfully"));
        } catch (Exception e) {
            return new ResponseEntity<>(new RegisterResponse("Error updating user profile"), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("users/{id}/lock")
    public ResponseEntity<Void> lockUserAccount(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("users/{id}/unlock")
    public ResponseEntity<Void> unlockUserAccount(@PathVariable Long id) {
        userService.unlockUser(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(){
       UserDetailDTO  userDetailDTO = userService.getCurrentUser();
        return ResponseEntity.ok(userDetailDTO);
    }



}

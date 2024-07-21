package com.codegym.controller;

import com.codegym.model.Email;
import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.service.IPostService;
import com.codegym.service.IUserService;
import com.codegym.service.InfoUserService;
import com.codegym.service.imp.EmailService;
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
    @Autowired
    private IPostService postService;
    @Autowired
    EmailService emailService;
    @Autowired
    InfoUserService infoUserService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/lock/{id}")
    public ResponseEntity<String> lockUser(@PathVariable Long id) {
        userService.lockUser(id);
        return ResponseEntity.ok("User locked successfully");
    }
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost() {
        List<PostDTO> postList = postService.getAllPostInfo();
        return ResponseEntity.ok(postList);
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostDTOById(id);
        return ResponseEntity.ok(postDTO);
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        String title = postService.getPostById(id).getTitle();
        PostDTO postDTO = postService.getPostDTOById(id);
        String username = postDTO.getUsername();
        String userEmail = userService.findByUserName(username).getEmail();
        postService.deletePost(id);
        Email email = new Email(userEmail,
                "Important Information Regarding Your Recent Post ",
                "Dear " + username +
                        "We are writing to inform you that your recent post, titled" + title + "has been removed from THE BLOG.\n" +
                        "\n" +
                        "We understand that this may be disappointing news, and we want to assure you that this decision was not made lightly. After careful consideration, we determined that the post violated our [Community Guidelines/Terms of Service] due to [Reason for Removal].\n" +
                        "\n" +
                        "Specifically, the post was found to be [Explanation of Violation]. We believe that this content is not appropriate for our community and could potentially harm or offend other users.\n" +
                        "\n" +
                        "We encourage you to review our [Community Guidelines/Terms of Service] to ensure that your future posts comply with our standards. You can find them here: [Link to Guidelines].\n" +
                        "\n" +
                        "We value your contributions to our community and hope that you will continue to participate in a positive and constructive manner. If you have any questions or concerns about this decision, please do not hesitate to contact us at codegymvn@gmail.com.vn" +
                        "\n" +
                        "Sincerely,");
        emailService.sendSimpleEmail(email.getTo(), email.getSubject(), email.getText());
        return ResponseEntity.ok("post");
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

    @GetMapping("/search/{username}")
    public List<UserDetailDTO> searchUsers(@PathVariable("username") String username) {
        return userService.searchUsers(username);
    }
    @GetMapping("/posts/search")
    public ResponseEntity<?> getAllPostByTitle(@RequestParam String input) {
        List<PostDTO> searchResult = postService.adminFindPost(input);
        return ResponseEntity.ok(searchResult);
    }
}

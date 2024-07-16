package com.codegym.controller;

import com.codegym.model.*;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.payload.request.PostRequest;
import com.codegym.payload.response.RegisterResponse;
import com.codegym.repository.IPostRepository;
import com.codegym.repository.ITagRepository;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IModeService;
import com.codegym.service.IPostService;
import com.codegym.service.IUserService;
import com.codegym.service.InfoUserService;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    IPostService postService;
    @Autowired
    IPostRepository postRepository;
    @Autowired
    IUserService userService;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IModeService modeService;
    @Autowired
    InfoUserService infoUserService;
    @Autowired
    ITagRepository tagRepository;

    @PostMapping("/posts")
    public ResponseEntity<?> doPost(@RequestBody PostRequest postRequest) {
        String content = postRequest.getContent();
        String title = postRequest.getTitle();  // Sửa lại đúng method
        String description = postRequest.getDescription();
        LocalDate time = LocalDate.now();
        Mode mode = modeService.findModeById(postRequest.getMode_id());
        String image = postRequest.getImage();
        UserDetailDTO userDetailDTO1 = userService.getCurrentUser();
        User user = userService.getUserById(userDetailDTO1.getId());
        List<Comment> comments = new ArrayList<>();
        Post post = new Post(title, time, content, image, description, mode, user, comments);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : postRequest.getTags()) {
            Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                return tagRepository.save(newTag);
            });
            tags.add(tag);
        }
        post.setTags(tags);

        postRepository.save(post);
        return ResponseEntity.ok(new RegisterResponse("Post has been posted successfully"));
    }
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost() {
        List<PostDTO> postList = postService.getAllPostInfo();
        return ResponseEntity.ok(postList);
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") Long id) {
        PostDTO postDTO = postService.getPostDTOById(id);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") Long id,
                                        @RequestBody PostRequest postRequest) {
        Long postId = id;
        String title = postRequest.getTitle();
        LocalDate time = LocalDate.now();
        String content = postRequest.getContent();
        String image = postRequest.getImage();
        String description = postRequest.getDescription();
        Mode mode = modeService.findModeById( postRequest.getMode_id() );
        Long userId = userService.getCurrentUser().getId();
        User user = userService.getUserById(userId);
        Post post = postService.getPostById(id);
        List<Comment> comments = post.getComments();
        Post newPost = new Post(postId, title, time, content, image, description, mode, user, comments);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : postRequest.getTags()) {
            Tag tag = tagRepository.findByName(tagName).orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                return tagRepository.save(newTag);
            });
            tags.add(tag);
        }
        post.setTags(tags);

        postService.savePost(newPost);
        return ResponseEntity.ok("Post have been updated");
    }
}

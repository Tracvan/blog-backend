package com.codegym.controller;

import com.codegym.model.Post;
import com.codegym.model.React;
import com.codegym.model.User;
import com.codegym.service.IPostService;
import com.codegym.service.IReactService;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/react")
public class ReactController {
    @Autowired
    IReactService reactService;
    @Autowired
    IPostService postService;
    @Autowired
    IUserService userService;

    @PostMapping("/")
    public ResponseEntity<?> likeAPost(@RequestParam Long postId) {
        User user = userService.getUserById(userService.getCurrentUser().getId());
        Post post = postService.getPostById(postId);
        React react = new React(user, post);
        reactService.createLike(react);
        return ResponseEntity.ok("Post is liked");
    }
}

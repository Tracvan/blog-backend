package com.codegym.controller;

import com.codegym.model.Comment;
import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.payload.request.CommentRequest;
import com.codegym.service.ICommentService;
import com.codegym.service.IPostService;
import com.codegym.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    ICommentService commentService;
    @Autowired
    IUserService userService;
    @Autowired
    IPostService postService;
    @PostMapping("")
    public ResponseEntity<?> createComment (@RequestBody CommentRequest commentRequest){
         String content = commentRequest.getContent();
        LocalDate time = LocalDate.now();
        UserDetailDTO userDTO = userService.getCurrentUser();
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        Post post = postService.getPostById(commentRequest.getPost_id());
        Comment comment = new Comment(content, time, post,user);
        commentService.createComment(comment);
        return  ResponseEntity.ok(comment);
    }
}

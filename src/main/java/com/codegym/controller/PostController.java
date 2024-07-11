package com.codegym.controller;

import com.codegym.model.Comment;
import com.codegym.model.Mode;
import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.payload.request.PostRequest;
import com.codegym.payload.response.RegisterResponse;
import com.codegym.repository.IPostRepository;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IModeService;
import com.codegym.service.IPostService;
import com.codegym.service.IUserService;
import com.codegym.service.InfoUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

@PostMapping("/posts")
public ResponseEntity<?> doPost(@RequestBody PostRequest postRequest) {
    String content = postRequest.getContent();
    String title = postRequest.getTitle();  // Sửa lại đúng method
    String description = postRequest.getDescription();
    LocalDate time = LocalDate.now();
    Mode mode =  modeService.findModeById(postRequest.getMode_id());
    String image = postRequest.getImage();

    UserDetailDTO userDetailDTO = userService.getCurrentUser();
    // Giả sử UserDetailDTO có method getId() để lấy id của user
    User user = userRepository.findById(userDetailDTO.getId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    List<Comment> comments = new ArrayList<>();


    Post post = new Post(title, time, content, image, description, mode, user, comments);
        postRepository.save(post);
    return ResponseEntity.ok(new RegisterResponse("Post has been posted successfully"));
}
@GetMapping("/posts")
    public ResponseEntity<?> getAllPost(){
    List<PostDTO> postList = postService.getAllPostInfo();
    return ResponseEntity.ok(postList);
}
@GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") Long id){
    PostDTO postDTO = postService.getPostById(id);
    return  ResponseEntity.ok(postDTO);
}

}

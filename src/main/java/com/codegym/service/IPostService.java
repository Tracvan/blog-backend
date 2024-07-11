package com.codegym.service;

import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDetailDTO;
import com.codegym.payload.request.PostRequest;

import java.util.List;

public interface IPostService {
    void createPost(Post post);
    UserDetailDTO getUserDetailByUser(User user);
    List<PostDTO> getAllPostInfo();
    PostDTO getPostById(Long id);
}

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
    PostDTO getPostDTOById(Long id);
    Post getPostById(Long id);
    void savePost(Post post);
    void deletePost(Long id);
    List<PostDTO> getAllPublicPostInfo(int page, int size);
    List<PostDTO> getAllMyPost(int page, int size);
    List<PostDTO> findPostByTitle(String input);
    List<PostDTO> findMyPostByTitle( String input);
    List<PostDTO> adminFindPost(String input);
}

package com.codegym.service.imp;

import com.codegym.model.Post;
import com.codegym.payload.request.PostRequest;
import com.codegym.repository.IPostRepository;
import com.codegym.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    @Autowired
    IPostRepository postRepository;
    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }
}

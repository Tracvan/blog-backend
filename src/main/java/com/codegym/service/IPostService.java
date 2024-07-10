package com.codegym.service;

import com.codegym.model.Post;
import com.codegym.payload.request.PostRequest;

public interface IPostService {
    void createPost(Post post);
}

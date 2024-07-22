package com.codegym.service;

import com.codegym.model.Post;
import com.codegym.model.React;
import com.codegym.model.User;
import org.springframework.data.domain.Example;

public interface IReactService {
    void createLike(React react);
    boolean checkIsLiked(Long postId);
    void deleteLiked(Post post, User user);
}

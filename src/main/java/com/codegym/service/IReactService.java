package com.codegym.service;

import com.codegym.model.React;
import org.springframework.data.domain.Example;

public interface IReactService {
    void createLike(React react);
    boolean checkIsLiked(Long postId);
}

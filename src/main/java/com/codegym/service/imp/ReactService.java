package com.codegym.service.imp;

import com.codegym.model.Post;
import com.codegym.model.React;
import com.codegym.model.User;
import com.codegym.repository.IUserRepository;
import com.codegym.repository.ReactRepository;
import com.codegym.service.IPostService;
import com.codegym.service.IReactService;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReactService implements IReactService {
    @Autowired
    ReactRepository reactRepository;
    @Autowired
    IUserService userService;
    @Autowired
    IPostService postService;
    @Override
    public void createLike(React react) {
        reactRepository.save(react);
    }

    @Override
    public boolean checkIsLiked(Long postId) {
        Post post = postService.getPostById(postId);
        String username = userService.getCurrentUser().getUsername();
        User user = userService.findByUserName(username);
        Long count = reactRepository.countByPostAndUser(post,user);
        if(count != 0){
            return true;
        }else{
            return false;
        }
    }
}

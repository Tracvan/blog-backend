package com.codegym.repository;

import com.codegym.model.Post;
import com.codegym.model.React;
import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactRepository extends JpaRepository<React, Long> {
    long countByPostAndUser(Post post, User user);
    React findByPostAndUser(Post post, User user);
}


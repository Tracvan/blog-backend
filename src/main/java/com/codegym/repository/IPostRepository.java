package com.codegym.repository;

import com.codegym.model.Post;
import com.codegym.model.dto.UserDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

}

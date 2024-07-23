package com.codegym.repository;

import com.codegym.model.Post;
import com.codegym.model.User;
import com.codegym.model.dto.PostDTO;
import com.codegym.model.dto.UserDetailDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT new com.codegym.model.dto.PostDTO(p.id, p.title, p.time, p.content, p.image, p.description, p.mode, p.user.username) FROM Post p where p.mode.id = 1 order by p.id ASC ")
    List<PostDTO> getAllPublicPost(Pageable pageable);

    @Query("SELECT  new com.codegym.model.dto.PostDTO(p.id, p.title, p.time, p.content, p.image, p.description, p.mode, p.user.username ) FROM Post p where p.user.id = :id order by p.time ASC")
    List<PostDTO> getAllByUser(Pageable pageable, Long id);

    @Query("SELECT new com.codegym.model.dto.PostDTO(p.id, p.title, p.time, p.content, p.image, p.description, p.mode, p.user.username ) FROM Post p where p.title LIKE %:title% AND p.mode.id = 1 order by p.time ASC ")
    List<PostDTO> findByTitle( String title);
    @Query("SELECT new com.codegym.model.dto.PostDTO(p.id, p.title, p.time, p.content, p.image, p.description, p.mode, p.user.username) FROM Post p WHERE p.title LIKE %:title% AND p.user.username = :username ORDER BY p.time ASC")
    List<PostDTO> findMyPostByTitle(String title, String username);
    @Query("SELECT new com.codegym.model.dto.PostDTO(p.id, p.title, p.time, p.content, p.image, p.description, p.mode, p.user.username ) FROM Post p where p.title LIKE %:title%  order by p.time ASC ")
    List<PostDTO> adminFindPost( String title);
}
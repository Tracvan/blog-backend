package com.codegym.repository;

import com.codegym.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}

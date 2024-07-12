package com.codegym.service;

import com.codegym.model.Comment;
import com.codegym.model.dto.CommentDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICommentService {
    void createComment(Comment comment);
}

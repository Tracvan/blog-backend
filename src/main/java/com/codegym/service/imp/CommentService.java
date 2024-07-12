package com.codegym.service.imp;

import com.codegym.model.Comment;
import com.codegym.model.dto.CommentDTO;
import com.codegym.repository.ICommentRepository;
import com.codegym.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentService implements ICommentService {
    @Autowired
    ICommentRepository commentRepository;
    @Override
    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }
}

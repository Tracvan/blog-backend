package com.codegym.model.dto;

import com.codegym.model.Comment;
import com.codegym.model.Mode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
    private Long id;
    private String title;
    private LocalDate time;
    private String content;
    private String image;
    private String description;
    private Mode mode;
    private String userAvatar;
    private String username;
    private List<CommentDTO> commentsDTO;

}

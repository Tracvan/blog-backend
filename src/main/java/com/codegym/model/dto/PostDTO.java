package com.codegym.model.dto;

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
    private Boolean isOwner;
    private Boolean isReacted;

    public PostDTO(Long id, String title, LocalDate time, String content, String image, String description, Mode mode, String username) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.username = username;
    }

    public PostDTO(Long id, String title, LocalDate time, String content, String image, String description, Mode mode, String username, boolean isOwner, boolean isReacted) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.username = username;
        this.isOwner = isOwner;
        this.isReacted = isReacted;
    }
    public PostDTO(Long id, String title, LocalDate time, String content, String image, String description, Mode mode, String userAvatar, String username, List<CommentDTO> commentDTOList) {
         this.id =id;
         this.title = title;
         this.time = time;
         this.content = content;
         this.image = image;
         this.description = description;
        this.mode =mode;
        this.userAvatar = userAvatar;
        this.username = username;
        this.commentsDTO = commentDTOList;

    }

    public PostDTO(Long postId, String title, LocalDate time, String content, String image, String description, Mode mode, String userAvatar, String username, List<CommentDTO> commentDTOList, boolean isOwner) {
        this.id = postId;
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.userAvatar = userAvatar;
        this.username = username;
        this.isOwner = isOwner;
    }
}

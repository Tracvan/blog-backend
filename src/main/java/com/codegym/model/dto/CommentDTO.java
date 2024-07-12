package com.codegym.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDate time;
    private String userAvatar;
    private String username;

    public CommentDTO(Long id, String content, LocalDate time, String userAvatar, String username) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.userAvatar = userAvatar;
        this.username = username;
    }
}

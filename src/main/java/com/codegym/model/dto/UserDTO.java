package com.codegym.model.dto;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String username;
    private LocalDate date;
    private String avatar;
    private String fullName;
    private String status;


    public UserDTO() {}

    public UserDTO(Long id, String username, LocalDate date, String avatar, String fullName, String status) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.avatar = avatar;
        this.fullName = fullName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.codegym.model.dto;

import java.time.LocalDate;

public class UserDetailDTO {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private LocalDate date;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String status;


    public UserDetailDTO() {}

    public UserDetailDTO(Long id, String username, String email, String avatar, LocalDate date, String fullName, String address, String phoneNumber, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

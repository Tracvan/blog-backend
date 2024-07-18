package com.codegym.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserProfileUpdateDTO {
    private Long id;
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    private String avatar;

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    public UserProfileUpdateDTO() {
    }

    public UserProfileUpdateDTO(Long id, String username, String email, String avatar, String fullName, String address, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
}

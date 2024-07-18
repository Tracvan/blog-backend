package com.codegym.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "info_user")
public class InfoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private LocalDate createdAt;
    private String avatar;
    private String fullName;
    private String address;
    private String phonenumber;
    private String status;

    public InfoUser(User user, LocalDate createdAt, String avatar, String fullName, String status){
        this.user = user;
        this.createdAt = createdAt;
        this.avatar = avatar;
        this.fullName = fullName;
        this.status = status;

    }

}


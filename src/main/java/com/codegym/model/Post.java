package com.codegym.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private LocalDate time;
    private String content;
    private String image;
    private String description;
    @ManyToOne
    @JoinColumn(name = "mode_id", nullable = false)
    private Mode mode;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, LocalDate time, String content, String image, String description, Mode mode, User user) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.user = user;
    }
}

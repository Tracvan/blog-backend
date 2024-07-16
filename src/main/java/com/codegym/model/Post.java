package com.codegym.model;

import com.codegym.model.dto.CommentDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})


public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDate time;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String image;
    private String description;
    @ManyToOne
    @JoinColumn(name = "mode_id", nullable = false)
    private Mode mode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Post(String title, LocalDate time, String content, String image, String description, Mode mode, User user, List<Comment> comments ) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.user = user;
        this.comments = comments;
    }

    public Post(Long id, String title, LocalDate time, String content, String image, String description, Mode mode, User user) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
        this.description = description;
        this.mode = mode;
        this.user = user;
    }

    public Post(Long postId, String title, LocalDate time, String content, String image, String description, Mode mode, User user, List<Comment> comments) {
    }
}

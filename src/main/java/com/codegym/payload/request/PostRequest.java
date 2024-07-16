
package com.codegym.payload.request;

import com.codegym.model.Mode;
import com.codegym.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    @NotBlank
    private String content;

    @NotBlank
    private String description;
    @Nullable
    private String image;
    @NotBlank

    @NotBlank
    private String title;

    @NotNull
    private Long mode_id;

    private Set<String> tags;
}

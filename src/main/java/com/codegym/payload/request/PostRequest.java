
package com.codegym.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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


}

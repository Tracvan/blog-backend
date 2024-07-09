package com.codegym.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class LoginResponse {

    @NotBlank
    private String message;

    @Nullable
    private String token;
    @Nullable
    private String authorize;

    public LoginResponse() {
        super();
    }

    public LoginResponse(@NotBlank String message, String token, String authorize) {
        super();
        this.message = message;
        this.token = token;
        this.authorize = authorize;
    }
}

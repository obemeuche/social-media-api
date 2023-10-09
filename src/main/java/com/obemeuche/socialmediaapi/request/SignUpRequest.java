package com.obemeuche.socialmediaapi.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "Invalid username")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Invalid username")
    private String username;

    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Invalid password")
    @Size(min = 8, max = 13, message = "Invalid password")
    @Pattern(regexp = "^[0-9A-Za-z \\\\\\\\s.,-]+$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "Invalid password")
    private String confirmPassword;
}

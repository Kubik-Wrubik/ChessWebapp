package com.kubik.ChessWebapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChessUserDto {
    private Long id;
        @Email(message = "Invalid email")
    private String email;
        @Size(min = 3, message = "Cannot be less that 3")
    private String nickname;
        @Size(min = 3, message = "Cannot be less that 3")
    private String password;
    private String confirmPassword;
}

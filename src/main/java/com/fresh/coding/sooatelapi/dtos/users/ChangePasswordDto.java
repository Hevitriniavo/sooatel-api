package com.fresh.coding.sooatelapi.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePasswordDto implements Serializable {

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Old password is required.")
    private String oldPassword;

    @NotBlank(message = "New password is required.")
    @Size(min = 4, message = "New password must be at least 4 characters long.")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required.")
    private String confirmPassword;
}

package com.fresh.coding.sooatelapi.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
sealed abstract class UserBase permits CreateUser, UpdateUser, SignInUser {

    @NotBlank(message = "Email and Password cannot be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}

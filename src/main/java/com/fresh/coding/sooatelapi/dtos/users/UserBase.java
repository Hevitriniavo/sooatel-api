package com.fresh.coding.sooatelapi.dtos.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
sealed abstract class UserBase permits CreateUser, SignInUser, UpdateUser, UserSummarized {

    @NotBlank(message = "Email and Password cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}

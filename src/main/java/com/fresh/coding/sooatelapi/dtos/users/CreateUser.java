package com.fresh.coding.sooatelapi.dtos.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class CreateUser extends UserBase implements Serializable {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    public CreateUser(
            String email,
            String password,
            String username
    ) {
        super(email, password);
        this.username = username;
    }
}

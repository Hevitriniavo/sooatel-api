package com.fresh.coding.sooatelapi.dtos.users;


import java.io.Serializable;

public final class SignInUser extends UserBase implements Serializable {
    public SignInUser(String email, String password) {
        super(email, password);
    }
}

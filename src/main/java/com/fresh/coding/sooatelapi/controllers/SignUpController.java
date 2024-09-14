package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import com.fresh.coding.sooatelapi.dtos.users.CreateUser;
import com.fresh.coding.sooatelapi.services.auth.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-up")
@Validated
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken signUp(@RequestBody @Valid CreateUser createUser) {
        return signUpService.signUp(createUser);
    }
}

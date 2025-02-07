package com.fresh.coding.sooatelapi.controllers.auth;

import com.fresh.coding.sooatelapi.dtos.token.AuthToken;
import com.fresh.coding.sooatelapi.dtos.users.CreateUser;
import com.fresh.coding.sooatelapi.dtos.users.UserSummarized;
import com.fresh.coding.sooatelapi.services.auth.SignUpService;
import com.fresh.coding.sooatelapi.services.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;
    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken signUp(@RequestBody @Valid CreateUser createUser) {
        return signUpService.signUp(createUser);
    }

    @GetMapping("/users")
    public List<UserSummarized> all() {
        return userService.findAll();
    }


    @DeleteMapping("/users/{id}")
    public UserSummarized delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}

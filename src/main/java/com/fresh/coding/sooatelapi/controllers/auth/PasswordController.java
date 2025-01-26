package com.fresh.coding.sooatelapi.controllers.auth;


import com.fresh.coding.sooatelapi.dtos.users.ChangePasswordDto;
import com.fresh.coding.sooatelapi.services.auth.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;


    @PostMapping("/change")
    public String changePassword(
            @Valid @RequestBody
            ChangePasswordDto changePasswordDto
    ) {
        return passwordService.changePassword(changePasswordDto);
    }
}

package com.fresh.coding.sooatelapi.dtos.otp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDto {

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "OTP code is required")
    @Size(min = 6, max = 6, message = "OTP code must be exactly 6 characters long")
    private String otpCode;

    @NotNull(message = "New password is required")
    private String newPassword;
}

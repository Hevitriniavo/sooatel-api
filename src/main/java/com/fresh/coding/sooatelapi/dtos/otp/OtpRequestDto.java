package com.fresh.coding.sooatelapi.dtos.otp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequestDto implements Serializable {

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "OTP code is required")
    private String otpCode;
}

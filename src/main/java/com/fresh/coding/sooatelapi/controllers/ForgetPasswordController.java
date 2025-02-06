package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.otp.PasswordChangeDto;
import com.fresh.coding.sooatelapi.dtos.otp.OtpRequestDto;
import com.fresh.coding.sooatelapi.services.auth.ForgetPasswordService;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    private final ForgetPasswordService forgetPasswordService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        try {
            forgetPasswordService.sendOtp(email);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (HttpBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid OtpRequestDto otpRequestDto) {
        try {
            forgetPasswordService.verifyOtp(otpRequestDto);
            return ResponseEntity.ok("OTP is valid");
        } catch (HttpBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeDto passwordChangeDto) {
        try {
            forgetPasswordService.changePassword(passwordChangeDto);
            return ResponseEntity.ok("Password changed successfully");
        } catch (HttpBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

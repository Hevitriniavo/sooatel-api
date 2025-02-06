package com.fresh.coding.sooatelapi.services.auth;


import com.fresh.coding.sooatelapi.dtos.otp.OtpRequestDto;
import com.fresh.coding.sooatelapi.dtos.otp.PasswordChangeDto;

public interface ForgetPasswordService {
    void sendOtp(String email);

    void verifyOtp(OtpRequestDto otpRequestDto);

    void changePassword(PasswordChangeDto passwordChangeDto);
}

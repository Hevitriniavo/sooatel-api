package com.fresh.coding.sooatelapi.services.auth.impl;

import com.fresh.coding.sooatelapi.dtos.EmailDto;
import com.fresh.coding.sooatelapi.dtos.otp.OtpRequestDto;
import com.fresh.coding.sooatelapi.dtos.otp.PasswordChangeDto;
import com.fresh.coding.sooatelapi.entities.OtpCode;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.auth.ForgetPasswordService;
import com.fresh.coding.sooatelapi.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    private final EmailService emailService;
    private final RepositoryFactory repositoryFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendOtp(String email) {
        var otpCodeRepo = this.repositoryFactory.getOtpCodeRepository();
        var userRepo = this.repositoryFactory.getUserRepository();

        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new HttpBadRequestException("User with email " + email + " not found"));
          otpCodeRepo.deleteAllByUserId(user.getId());
        String otpCode = generateOtpCode();
        OtpCode otpCodeEntity = OtpCode.builder()
                .otpCode(otpCode)
                .user(user)
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();
        otpCodeRepo.save(otpCodeEntity);

        emailService.sendEmail(new EmailDto(email, otpCodeEntity));
    }

    @Override
    public void verifyOtp(OtpRequestDto otpRequestDto) {
        var otpCodeRepo = this.repositoryFactory.getOtpCodeRepository();
        var otpCodeEntity = otpCodeRepo.findByUserEmail(otpRequestDto.getEmail())
                .orElseThrow(() -> new HttpBadRequestException("OTP not found for this email"));

        if (otpCodeEntity.getOtpCode().equals(otpRequestDto.getOtpCode()) && otpCodeEntity.isExpired()) {
            otpCodeEntity.setIsVerified(true);
            otpCodeRepo.save(otpCodeEntity);
            return;
        }
        otpCodeRepo.deleteAllByUserId(otpCodeEntity.getUser().getId());
        throw new HttpBadRequestException("Invalid or expired OTP");
    }

    @Override
    public void changePassword(PasswordChangeDto passwordChangeDto) {
        var otpCodeRepo = this.repositoryFactory.getOtpCodeRepository();
        OtpCode otpCodeEntity = otpCodeRepo.findByUserEmail(passwordChangeDto.getEmail())
                .orElseThrow(() -> new HttpBadRequestException("OTP not found for this email"));

        if (otpCodeEntity.getOtpCode().equals(passwordChangeDto.getOtpCode())  && otpCodeEntity.getIsVerified()) {
            var userRepo = this.repositoryFactory.getUserRepository();
            var user = userRepo.findByEmail(passwordChangeDto.getEmail())
                    .orElseThrow(() -> new HttpBadRequestException("User not found"));

            String encodedPassword = passwordEncoder.encode(passwordChangeDto.getNewPassword());
            user.setPassword(encodedPassword);

            userRepo.save(user);
            otpCodeRepo.deleteAllByUserId(user.getId());
            return;
        }
        throw new HttpBadRequestException("OTP does not match");
    }

    private String generateOtpCode() {
        Random random = new Random();
        int otpCode = 100000 + random.nextInt(900000);
        return String.valueOf(otpCode);
    }
}

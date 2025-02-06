package com.fresh.coding.sooatelapi.services.email;

import com.fresh.coding.sooatelapi.dtos.EmailDto;
import com.fresh.coding.sooatelapi.entities.OtpCode;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailDto emailDto)  {
        OtpCode otpCode = emailDto.getOtpCode();
        String emailContent = generateEmailContent(otpCode);
        MimeMessage message = createMimeMessage(emailDto, emailContent);
        mailSender.send(message);
    }

    private String generateEmailContent(OtpCode otpCode) {
        StringOutput output = new StringOutput();
        templateEngine.render("emailTemplate.jte", otpCode, output);
        return output.toString();
    }

    private MimeMessage createMimeMessage(EmailDto emailDto, String emailContent) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(emailDto.getTo());
            String subject = formatSubject();
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            return message;
        } catch (MessagingException e) {
            throw new HttpBadRequestException("Failed to create MIME message: " + e.getMessage());
        }
    }

    private String formatSubject() {
        return "SOOATEL - Votre code OTP pour la récupération de mot de passe";
    }


}

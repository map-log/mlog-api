package com.mlog.user.service;

import com.mlog.config.MailConfigure;
import com.mlog.error.UnauthorizedException;
import com.mlog.user.repository.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final JavaMailSender javaMailSender;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailConfigure mailConfigure;
    private final Map<String, String> tokenStore = new HashMap<>();

    public Boolean sendPasswordResetMail(String email) {
        userMapper.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("not valid email"));
        String token = generateToken();
        tokenStore.put(token, email);
        MimeMessage message = createPasswordResetMail(email, token);
        javaMailSender.send(message);
        return true;
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(uuidAsString.getBytes());
    }

    private MimeMessage createPasswordResetMail(String email, String token) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(mailConfigure.getSenderEmail());
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("M-LOG 계정 비밀번호 재설정 요청");
            String body = "";
            body += "<div style=\"font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;\">";
            body += "<div style=\"max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">";
            body += "<img src=\"https://m-log-photo-s3.s3.ap-southeast-2.amazonaws.com/26e1ca9d-bf7f-41ee-89cb-103a7379270b.png\" style=\"display: block; margin: 0 auto 20px; width: 100px;\">";
            body += "<h2 style=\"color: #333; text-align: center;\">비밀번호 재설정 요청</h2>"; // 제목 색상 수정
            body += "<p>안녕하세요,</p>";
            body += "<p>M-LOG 계정 비밀번호 재설정을 요청하셨습니다. 아래 양식을 사용하여 비밀번호를 재설정하세요!</p>";
            body += "<form action=\"" + mailConfigure.getRoot() + "/user/reset-password\" method=\"post\" target=\"_blank\" style=\"background-color: #f9f9f9; padding: 20px; border: 1px solid #ddd; border-radius: 5px;\">";
            body += "<input type=\"hidden\" name=\"token\" value=\"" + token + "\">";
            body += "<label for=\"newPassword\" style=\"display: block; margin-bottom: 10px; font-weight: bold; color: #333;\">새 비밀번호:</label>";
            body += "<input type=\"password\" id=\"newPassword\" name=\"newPassword\" style=\"width: 90%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px;\">";
            body += "<button type=\"submit\" style=\"background-color: #333; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; width: 100%;\">비밀번호 재설정</button>";
            body += "</form>";
            body += "<p style=\"margin-top: 20px;\">감사합니다,<br>M-LOG 팀</p>";
            body += "</div>";
            body += "</div>";

            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public Boolean resetPassword(String token, String newPassword) {
        String email = tokenStore.get(token);
        if (email == null) {
            throw new UnauthorizedException("not valid email");
        }
        userMapper.updatePassword(email, passwordEncoder.encode(newPassword));
        tokenStore.remove(token);
        return true;
    }
}

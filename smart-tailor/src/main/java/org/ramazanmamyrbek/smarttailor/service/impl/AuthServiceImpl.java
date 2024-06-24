package org.ramazanmamyrbek.smarttailor.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ramazanmamyrbek.smarttailor.exception.InvalidVerificationCodeException;
import org.ramazanmamyrbek.smarttailor.security.JwtProvider;
import org.ramazanmamyrbek.smarttailor.security.JwtUserDetails;
import org.ramazanmamyrbek.smarttailor.service.AuthService;
import org.ramazanmamyrbek.smarttailor.service.EmailService;
import org.ramazanmamyrbek.smarttailor.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {
    final EmailService emailService;
    final UserService userService;
    final JwtProvider jwtProvider;
    @Override
    public void sendVerificationCode(String email) {
        if(!userService.getUserByEmail(email).isPresent()) {
            throw new UsernameNotFoundException("User is not found with email: " + email);
        }
        UUID uuid = UUID.randomUUID();
        String code = String.format("%04d", Math.abs(uuid.hashCode() % 10000));
        emailService.sendCode(email, code);
        userService.updateCode(email, code);
    }

    @Override
    public Map<String, String> verifyCode(String email, String code) {
        Map<String, String> tokens = new HashMap<>();
        String usersCode = userService.getCodeByUserEmail(email, code);
        if(usersCode== null || !usersCode.equals(code))
            throw new InvalidVerificationCodeException("Invalid verification code. Try to ask another code");
        userService.activateUser(email);
        JwtUserDetails userDetails = new JwtUserDetails(userService.getUserByEmail(email).get());
        tokens.put("accessToken", jwtProvider.generateAccessToken(userDetails));
        tokens.put("refreshToken", jwtProvider.generateRefreshToken(userDetails));
        return tokens;
    }

    @Override
    public void login(String email) {
        if(!userService.getUserByEmail(email).isPresent()) {
            throw new UsernameNotFoundException("User is not found with email: " + email);
        }
        UUID uuid = UUID.randomUUID();
        String code = String.format("%04d", Math.abs(uuid.hashCode() % 10000));
        emailService.sendCode(email, code);
        userService.updateCode(email, code);
    }
}

package kg.smarttailor.usersservice.service.impl;

import kg.smarttailor.usersservice.entity.User;
import kg.smarttailor.usersservice.exception.InvalidJwtException;
import kg.smarttailor.usersservice.exception.InvalidVerificationCodeException;
import kg.smarttailor.usersservice.security.JwtProvider;
import kg.smarttailor.usersservice.security.JwtUserDetails;
import kg.smarttailor.usersservice.service.AuthService;
import kg.smarttailor.usersservice.service.EmailService;
import kg.smarttailor.usersservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @Override
    public Map<String, String> refreshTokens(String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        if(!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidJwtException("Refresh token is invalid");
        }
        String email = jwtProvider.getEmail(refreshToken);
        User user = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not found with email: " + email));
        JwtUserDetails userDetails = new JwtUserDetails(user);
        tokens.put("accessToken", jwtProvider.generateAccessToken(userDetails));
        tokens.put("refreshToken", jwtProvider.generateRefreshToken(userDetails));
        return tokens;
    }
}

package kg.smarttailor.usersservice.service;

import java.util.Map;

public interface AuthService {
    void sendVerificationCode(String email);

    Map<String, String> verifyCode(String email, String code);

    void login(String email);

    Map<String, String> refreshTokens(String refreshToken);
}

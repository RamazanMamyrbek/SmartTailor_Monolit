package org.ramazanmamyrbek.smarttailor.service;

import java.util.Map;

public interface AuthService {
    void sendVerificationCode(String email);

    Map<String, String> verifyCode(String email, String code);

    void login(String email);
}

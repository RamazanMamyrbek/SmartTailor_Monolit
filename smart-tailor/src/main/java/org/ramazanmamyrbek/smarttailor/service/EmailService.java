package org.ramazanmamyrbek.smarttailor.service;

public interface EmailService {
    void sendCode(String toEmail, String code);
}

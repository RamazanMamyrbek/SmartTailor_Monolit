package kg.smarttailor.usersservice.service;

public interface EmailService {
    void sendCode(String toEmail, String code);
}

package kg.smarttailor.usersservice.service;

import kg.smarttailor.usersservice.dto.RegisterRequestDto;
import kg.smarttailor.usersservice.entity.User;

import java.util.Optional;

public interface UserService {
    void create(RegisterRequestDto registerRequestDto);

    String getCodeByUserEmail(String email, String code);

    void activateUser(String email);

    Optional<User> getUserByEmail(String email);

    void updateCode(String email, String code);
}

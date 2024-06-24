package org.ramazanmamyrbek.smarttailor.service;

import org.ramazanmamyrbek.smarttailor.dto.RegisterRequestDto;
import org.ramazanmamyrbek.smarttailor.entity.User;

import java.util.Optional;

public interface UserService {
    void create(RegisterRequestDto registerRequestDto);

    String getCodeByUserEmail(String email, String code);

    void activateUser(String email);

    Optional<User> getUserByEmail(String email);

    void updateCode(String email, String code);
}

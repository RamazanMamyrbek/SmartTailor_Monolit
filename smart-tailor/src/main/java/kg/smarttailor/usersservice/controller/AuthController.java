package kg.smarttailor.usersservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kg.smarttailor.usersservice.constant.ResponseMessageConstants;
import kg.smarttailor.usersservice.dto.RegisterRequestDto;
import kg.smarttailor.usersservice.dto.ResponseDto;
import kg.smarttailor.usersservice.service.AuthService;
import kg.smarttailor.usersservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "Auth controller")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    final AuthService authService;
    final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterRequestDto reqisterRequest) {
        userService.create(reqisterRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_200, ResponseMessageConstants.MESSAGE_200));
    }

    @PostMapping("/send-code")
    @Operation(summary = "Send verification code again")
    public ResponseEntity<ResponseDto> sendVerificationCode(@RequestParam @Valid @Email String email) {
        authService.sendVerificationCode(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_200, ResponseMessageConstants.MESSAGE_200));
    }

    @PostMapping("/verify-code")
    @Operation(summary = "Verify the verification code")
    public ResponseEntity<?> verifyCode(@RequestParam @Valid @Email(message = "Should be valid email") String email,
                                        @RequestParam @Valid @Pattern(regexp = "\\d{4}", message = "The code must contain only 4 digits") String code) {
        Map<String, String> tokens = authService.verifyCode(email, code);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "accessToken", tokens.get("accessToken"), "refreshToken", tokens.get("refreshToken"), "message", ResponseMessageConstants.MESSAGE_201
                ));
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@RequestParam @Email(message = "Should be valid email") @Valid String email) {
        authService.login(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_200, ResponseMessageConstants.MESSAGE_200));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh tokens")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        Map<String, String> tokens = authService.refreshTokens(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "accessToken", tokens.get("accessToken"), "refreshToken", tokens.get("refreshToken"), "message", ResponseMessageConstants.MESSAGE_200
        ));
    }

}

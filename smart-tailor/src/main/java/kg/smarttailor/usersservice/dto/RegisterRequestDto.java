package kg.smarttailor.usersservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequestDto {
    @Schema(description = "Last name", example = "Doe")
    String lastName;
    @Schema(description = "First name", example = "John")
    String firstName;
    @Schema(description = "Patronymic name", example = "Petrovich")
    String patronymicName;
    @Email(message = "Should be email format")
    @Schema(description = "Email", example = "jacknot343@gmail.com")
    String email;
    @Schema(description = "Phone number", example = "87776665544")
    String phoneNumber;
}

package org.ramazanmamyrbek.smarttailor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Dto for response of service")
public class ServiceResponseDto {
    @Schema(description = "Service id", example = "1")
    Long id;
    @Schema(description = "Service title", example = "Нужен утюжник")
    String title;
    @Schema(description = "Service description", example = "Нужен утюжник для организации")
    String description;
    @Schema(description = "Author contact", example = "jacknot343@gmail.com")
    String contact;
    @Schema(description = "Author id", example = "1")
    Long authorId;
    @Schema(description = "Author name", example = "John Doe")
    String authorFullName;
}

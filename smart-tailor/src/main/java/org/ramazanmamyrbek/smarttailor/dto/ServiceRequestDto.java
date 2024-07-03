package org.ramazanmamyrbek.smarttailor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Dto for creating a service")
public class ServiceRequestDto {
    @Schema(description = "Service title", example = "Нужен утюжник")
    String title;
    @Schema(description = "Service description", example = "Нужен утюжник для организации")
    String description;
    @Schema(description = "Author contact", example = "jacknot343@gmail.com")
    String contact;
}

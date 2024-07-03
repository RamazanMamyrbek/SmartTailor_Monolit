package org.ramazanmamyrbek.smarttailor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Dto for creating an inventory")
public class InventoryRequestDto {
    @Schema(description = "Service title", example = "Швейная машинка")
    String title;
    @Schema(description = "Service description", example = "Продается швейная машинка")
    String description;
    @Schema(description = "Author contact", example = "jacknot343@gmail.com")
    String contact;
    Long price;
}

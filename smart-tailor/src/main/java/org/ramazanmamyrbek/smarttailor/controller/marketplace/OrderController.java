package org.ramazanmamyrbek.smarttailor.controller.marketplace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ramazanmamyrbek.smarttailor.constant.ResponseMessageConstants;
import org.ramazanmamyrbek.smarttailor.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Endpoints for orders management")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/marketplace/orders")
@Validated
public class OrderController {

    @GetMapping
    @Operation(summary = "Lists all available orders")
    public ResponseEntity<ResponseDto> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_200, ResponseMessageConstants.MESSAGE_200));
    }
}

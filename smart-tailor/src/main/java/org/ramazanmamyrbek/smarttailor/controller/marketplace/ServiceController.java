package org.ramazanmamyrbek.smarttailor.controller.marketplace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ramazanmamyrbek.smarttailor.constant.ResponseMessageConstants;
import org.ramazanmamyrbek.smarttailor.dto.ResponseDto;
import org.ramazanmamyrbek.smarttailor.dto.ServiceRequestDto;
import org.ramazanmamyrbek.smarttailor.dto.ServiceResponseDto;
import org.ramazanmamyrbek.smarttailor.service.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Tag(name = "Endpoints for services management")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/marketplace/services")
@Validated
public class ServiceController {
    final ServiceService serviceService;

    @GetMapping
    @Operation(summary = "Lists all available services")
    public ResponseEntity<ResponseDto> getAll() {
        List<ServiceResponseDto> serviceResponseDtos = serviceService.getAllHiddenIsFalse();
         return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_200, ResponseMessageConstants.MESSAGE_200, serviceResponseDtos));
    }

    @PostMapping
    @Operation(summary = "Create a service")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody ServiceRequestDto serviceRequestDto, Principal principal) {
        serviceService.create(serviceRequestDto, principal.getName());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(ResponseMessageConstants.STATUS_201, ResponseMessageConstants.MESSAGE_201));
    }
}

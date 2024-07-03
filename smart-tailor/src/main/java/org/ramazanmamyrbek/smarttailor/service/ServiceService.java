package org.ramazanmamyrbek.smarttailor.service;

import org.ramazanmamyrbek.smarttailor.dto.ServiceRequestDto;
import org.ramazanmamyrbek.smarttailor.dto.ServiceResponseDto;

import java.util.List;

public interface ServiceService {
    List<ServiceResponseDto> getAllHiddenIsFalse();

    void create(ServiceRequestDto serviceRequestDto, String email);
}

package org.ramazanmamyrbek.smarttailor.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.ramazanmamyrbek.smarttailor.dto.ServiceRequestDto;
import org.ramazanmamyrbek.smarttailor.dto.ServiceResponseDto;
import org.ramazanmamyrbek.smarttailor.entity.Services;
import org.ramazanmamyrbek.smarttailor.entity.User;
import org.ramazanmamyrbek.smarttailor.repository.ServiceRepository;
import org.ramazanmamyrbek.smarttailor.service.ServiceService;
import org.ramazanmamyrbek.smarttailor.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class ServiceServiceImpl implements ServiceService {
    final ServiceRepository serviceRepository;
    final UserService userService;
    final ModelMapper modelMapper;

    @Override
    public List<ServiceResponseDto> getAllHiddenIsFalse() {
        List<Services> services =  serviceRepository.findAllByHiddenIsFalse();
        List<ServiceResponseDto> serviceResponseDtos = services.stream().map(service -> {
            ServiceResponseDto serviceResponseDto = modelMapper.map(service, ServiceResponseDto.class);
            serviceResponseDto.setAuthorId(service.getAuthor().getId());
            serviceResponseDto.setAuthorFullName(service.getAuthor().getFirstName() + " " + service.getAuthor().getLastName() + " " + service.getAuthor().getPatronymicName());
            return serviceResponseDto;
        }).toList();
        return serviceResponseDtos;
    }

    @Override
    @Transactional
    public void create(ServiceRequestDto serviceRequestDto, String email) {
        User user = userService.getUserByEmail(email).get();
        Services service = modelMapper.map(serviceRequestDto, Services.class);
        service.setAuthor(user);
        serviceRepository.save(service);
    }
}

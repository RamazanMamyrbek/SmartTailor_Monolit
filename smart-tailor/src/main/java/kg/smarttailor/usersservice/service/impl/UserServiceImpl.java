package kg.smarttailor.usersservice.service.impl;

import kg.smarttailor.usersservice.dto.RegisterRequestDto;
import kg.smarttailor.usersservice.entity.Role;
import kg.smarttailor.usersservice.entity.User;
import kg.smarttailor.usersservice.exception.UserAlreadyExistsException;
import kg.smarttailor.usersservice.repository.UserRepository;
import kg.smarttailor.usersservice.service.EmailService;
import kg.smarttailor.usersservice.service.RoleService;
import kg.smarttailor.usersservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final EmailService emailService;
    final ModelMapper modelMapper;
    final RoleService roleService;

    @Override
    @Transactional
    public void create(RegisterRequestDto registerRequestDto) {
        Optional<User> possibleUser = userRepository.findUserByEmail(registerRequestDto.getEmail());
        if(possibleUser.isPresent() && possibleUser.get().isEnabled()) {
            throw new UserAlreadyExistsException("User with this email is already registered. Try to login");
        }
        UUID uuid = UUID.randomUUID();
        String code = String.format("%04d", Math.abs(uuid.hashCode() % 10000));
        if(!possibleUser.isPresent()) {
            User user = new User();
            modelMapper.map(registerRequestDto, user);
            user.setCode(code);
            user.setCodeExpirationDate(LocalDateTime.now().plusMinutes(5));
            Role role = Role.builder().users(List.of(user)).name("DemoRole").permissions(Collections.emptySet()).build();
            roleService.create(role);
            user.setRole(role);
            userRepository.save(user);
        }else {
            User user = possibleUser.get();
            user.setCode(code);
            user.setCodeExpirationDate(LocalDateTime.now().plusMinutes(5));
            Role role = Role.builder().users(List.of(user)).name("DemoRole").permissions(Collections.emptySet()).build();
            roleService.create(role);
            user.setRole(role);
            userRepository.save(user);
        }
        emailService.sendCode(registerRequestDto.getEmail(), code);
    }

    @Override
    public String getCodeByUserEmail(String email, String code) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User is not found with email: " + email));
        return user.getCode();
    }

    @Override
    @Transactional
    public void activateUser(String email) {
        User user = userRepository.findUserByEmail(email).get();
        user.setEnabled(true);
        user.setCode(null);
        //There code can be removed from user if it needs
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void updateCode(String email, String code) {
        userRepository.findUserByEmail(email).get().setCode(code);
    }
}

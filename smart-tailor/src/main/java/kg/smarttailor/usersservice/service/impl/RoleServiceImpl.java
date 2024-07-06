package kg.smarttailor.usersservice.service.impl;

import kg.smarttailor.usersservice.entity.Role;
import kg.smarttailor.usersservice.repository.RoleRepository;
import kg.smarttailor.usersservice.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    final RoleRepository roleRepository;

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }
}

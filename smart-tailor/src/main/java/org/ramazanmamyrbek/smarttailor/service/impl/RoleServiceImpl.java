package org.ramazanmamyrbek.smarttailor.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.ramazanmamyrbek.smarttailor.entity.Role;
import org.ramazanmamyrbek.smarttailor.repository.RoleRepository;
import org.ramazanmamyrbek.smarttailor.service.RoleService;
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

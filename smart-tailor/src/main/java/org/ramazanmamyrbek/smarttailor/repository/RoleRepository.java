package org.ramazanmamyrbek.smarttailor.repository;

import org.ramazanmamyrbek.smarttailor.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

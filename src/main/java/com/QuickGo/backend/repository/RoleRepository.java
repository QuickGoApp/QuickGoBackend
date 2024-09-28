package com.QuickGo.backend.repository;


import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    List<Role> findByNameIn(List<ERole> roleDriver);
}

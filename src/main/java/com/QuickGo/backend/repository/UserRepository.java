package com.QuickGo.backend.repository;


import com.QuickGo.backend.models.Role;
import com.QuickGo.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findTopByOrderByIdDesc();

    List<User> findByUserCodeIn(List<String> userCodes);

    List<User> findByVehicleIsNotNull();

    List<User> findByRolesContains(Role role);

}

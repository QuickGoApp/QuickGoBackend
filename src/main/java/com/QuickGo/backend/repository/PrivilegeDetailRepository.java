package com.QuickGo.backend.repository;

import com.QuickGo.backend.models.Privilege;
import com.QuickGo.backend.models.PrivilegeDetails;
import com.QuickGo.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeDetailRepository extends JpaRepository<PrivilegeDetails,Integer> {

    Optional<PrivilegeDetails> findByPrivilegeAndRole(Privilege privilege, Role user);


    @Query("select usp.privilege from PrivilegeDetails usp where usp.role = :role and usp.status = 1")
    List<Privilege> findPrivilegeIdsByRole(@Param("role") Role role);
}

package com.zyloavenue.api.feature.user.repository;

import com.zyloavenue.api.feature.user.entity.RoleEntity;
import com.zyloavenue.api.feature.user.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByName(RoleName name);
}


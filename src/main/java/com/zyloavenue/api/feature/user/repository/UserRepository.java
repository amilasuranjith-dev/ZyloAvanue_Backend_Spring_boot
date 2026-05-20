package com.zyloavenue.api.feature.user.repository;

import com.zyloavenue.api.feature.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);

	boolean existsByEmail(String email);
}


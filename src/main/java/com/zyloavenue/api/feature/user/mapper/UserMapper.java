package com.zyloavenue.api.feature.user.mapper;

import com.zyloavenue.api.feature.user.dto.RoleDto;
import com.zyloavenue.api.feature.user.dto.UserDetailDto;
import com.zyloavenue.api.feature.user.dto.UserSummaryDto;
import com.zyloavenue.api.feature.user.entity.RoleEntity;
import com.zyloavenue.api.feature.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserSummaryDto toSummaryDto(UserEntity entity) {
        if (entity == null) return null;

        return UserSummaryDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .enabled(entity.isEnabled())
                .roleNames(entity.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()))
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public UserDetailDto toDetailDto(UserEntity entity) {
        if (entity == null) return null;

        return UserDetailDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .enabled(entity.isEnabled())
                .roles(entity.getRoles().stream()
                        .map(this::toRoleDto)
                        .collect(Collectors.toSet()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public RoleDto toRoleDto(RoleEntity entity) {
        if (entity == null) return null;

        return RoleDto.builder()
                .id(entity.getId())
                .name(entity.getName().name())
                .description(entity.getDescription())
                .build();
    }
}


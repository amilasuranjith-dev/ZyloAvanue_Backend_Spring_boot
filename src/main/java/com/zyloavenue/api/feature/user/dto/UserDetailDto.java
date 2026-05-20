package com.zyloavenue.api.feature.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    private Long id;
    private String email;
    private String fullName;
    private String phone;
    private boolean enabled;
    private Set<RoleDto> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


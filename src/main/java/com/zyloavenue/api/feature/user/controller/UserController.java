package com.zyloavenue.api.feature.user.controller;

import com.zyloavenue.api.common.api.ApiResponse;
import com.zyloavenue.api.feature.user.dto.UserDetailDto;
import com.zyloavenue.api.feature.user.dto.UserSummaryDto;
import com.zyloavenue.api.feature.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * List all users (paginated)
     * Only accessible to ADMIN role
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserSummaryDto>>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserSummaryDto> users = userService.listAll(pageable);
        return ResponseEntity.ok(ApiResponse.ok(users));
    }

    /**
     * Get user by ID
     * Only accessible to the user themselves or ADMIN
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDetailDto>> getById(@PathVariable Long id) {
        UserDetailDto user = userService.getById(id);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    /**
     * Get current user profile
     * Accessible to authenticated users
     */
    @GetMapping("/profile/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserDetailDto>> getCurrentUser(
            @RequestParam String email) {
        UserDetailDto user = userService.getByEmail(email);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    /**
     * Delete a user (soft delete)
     * Only accessible to ADMIN
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDetailDto>> deleteUser(@PathVariable Long id) {
        UserDetailDto deletedUser = userService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(deletedUser));
    }

    /**
     * Enable/Disable a user
     * Only accessible to ADMIN
     */
    @PatchMapping("/{id}/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserDetailDto>> updateEnabled(
            @PathVariable Long id,
            @RequestParam boolean enabled) {
        UserDetailDto user = userService.updateEnabled(id, enabled);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }
}


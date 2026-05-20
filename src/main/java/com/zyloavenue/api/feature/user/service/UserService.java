package com.zyloavenue.api.feature.user.service;

import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.user.dto.UserDetailDto;
import com.zyloavenue.api.feature.user.dto.UserSummaryDto;
import com.zyloavenue.api.feature.user.entity.UserEntity;
import com.zyloavenue.api.feature.user.mapper.UserMapper;
import com.zyloavenue.api.feature.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Get all users (paginated)
     */
    @Transactional(readOnly = true)
    public Page<UserSummaryDto> listAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toSummaryDto);
    }

    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public UserDetailDto getById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return userMapper.toDetailDto(user);
    }

    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public UserDetailDto getByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
        return userMapper.toDetailDto(user);
    }

    /**
     * Check if user exists by email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Soft delete a user
     */
    public UserDetailDto delete(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toDetailDto(user);
    }

    /**
     * Enable/disable a user
     */
    public UserDetailDto updateEnabled(Long id, boolean enabled) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        user.setEnabled(enabled);
        userRepository.save(user);
        return userMapper.toDetailDto(user);
    }
}


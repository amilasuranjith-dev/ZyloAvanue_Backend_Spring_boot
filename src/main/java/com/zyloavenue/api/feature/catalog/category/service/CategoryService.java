package com.zyloavenue.api.feature.catalog.category.service;

import com.zyloavenue.api.common.exception.BadRequestException;
import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.catalog.category.dto.CategoryDto;
import com.zyloavenue.api.feature.catalog.category.entity.CategoryEntity;
import com.zyloavenue.api.feature.catalog.category.mapper.CategoryMapper;
import com.zyloavenue.api.feature.catalog.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> listAllActive() {
        return categoryRepository.findByIsActiveAndDeletedAtIsNull(true)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> listAll() {
        return categoryRepository.findAll()
                .stream()
                .filter(cat -> cat.getDeletedAt() == null)
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
        if (category.getDeletedAt() != null) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        return categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public CategoryDto getBySlug(String slug) {
        CategoryEntity category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Category not found with slug: " + slug));
        if (category.getDeletedAt() != null) {
            throw new NotFoundException("Category not found with slug: " + slug);
        }
        return categoryMapper.toDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getSubcategories(Long parentId) {
        return categoryRepository.findByParentIdAndDeletedAtIsNull(parentId)
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDto create(CategoryDto dto) {
        // Check if slug already exists
        if (categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new BadRequestException("Category with slug '" + dto.getSlug() + "' already exists");
        }

        CategoryEntity entity = categoryMapper.toEntity(dto);
        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        if (category.getDeletedAt() != null) {
            throw new NotFoundException("Category not found with id: " + id);
        }

        // Check if slug already exists (and it's not the same category)
        if (!category.getSlug().equals(dto.getSlug()) &&
            categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new BadRequestException("Category with slug '" + dto.getSlug() + "' already exists");
        }

        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setParentId(dto.getParentId());
        category.setActive(dto.isActive());

        CategoryEntity updated = categoryRepository.save(category);
        return categoryMapper.toDto(updated);
    }

    //Delete a category (soft delete)
    public void delete(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        if (category.getDeletedAt() != null) {
            throw new NotFoundException("Category not found with id: " + id);
        }

        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}


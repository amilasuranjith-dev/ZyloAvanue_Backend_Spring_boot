package com.zyloavenue.api.feature.catalog.category.mapper;

import com.zyloavenue.api.feature.catalog.category.dto.CategoryDto;
import com.zyloavenue.api.feature.catalog.category.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(CategoryEntity entity) {
        if (entity == null) return null;

        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .parentId(entity.getParentId())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public CategoryEntity toEntity(CategoryDto dto) {
        if (dto == null) return null;

        return CategoryEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .slug(dto.getSlug())
                .parentId(dto.getParentId())
                .isActive(dto.isActive())
                .build();
    }
}


package com.zyloavenue.api.feature.catalog.category.repository;

import com.zyloavenue.api.feature.catalog.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findBySlug(String slug);
    List<CategoryEntity> findByParentIdAndDeletedAtIsNull(Long parentId);
    List<CategoryEntity> findByIsActiveAndDeletedAtIsNull(boolean isActive);
}


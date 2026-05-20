package com.zyloavenue.api.feature.catalog.product.service;

import com.zyloavenue.api.common.exception.BadRequestException;
import com.zyloavenue.api.common.exception.NotFoundException;
import com.zyloavenue.api.feature.catalog.product.dto.ProductDetailDto;
import com.zyloavenue.api.feature.catalog.product.dto.ProductSummaryDto;
import com.zyloavenue.api.feature.catalog.product.entity.ProductEntity;
import com.zyloavenue.api.feature.catalog.product.entity.ProductImageEntity;
import com.zyloavenue.api.feature.catalog.product.entity.ProductStatus;
import com.zyloavenue.api.feature.catalog.product.entity.ProductVariantEntity;
import com.zyloavenue.api.feature.catalog.product.mapper.ProductMapper;
import com.zyloavenue.api.feature.catalog.product.repository.ProductImageRepository;
import com.zyloavenue.api.feature.catalog.product.repository.ProductRepository;
import com.zyloavenue.api.feature.catalog.product.repository.ProductVariantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductMapper productMapper;

    public ProductService(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            ProductVariantRepository productVariantRepository,
            ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productVariantRepository = productVariantRepository;
        this.productMapper = productMapper;
    }

    /**
     * List all active products (paginated)
     */
    @Transactional(readOnly = true)
    public Page<ProductSummaryDto> listActive(Pageable pageable) {
        return productRepository.findByStatusAndDeletedAtIsNull(ProductStatus.ACTIVE, pageable)
                .map(product -> {
                    ProductImageEntity primaryImage = productImageRepository
                            .findByProductIdAndIsPrimaryAndDeletedAtIsNull(product.getId(), true);
                    return productMapper.toSummaryDto(product, primaryImage);
                });
    }

    /**
     * Get product by ID
     */
    @Transactional(readOnly = true)
    public ProductDetailDto getById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        if (product.getDeletedAt() != null) {
            throw new NotFoundException("Product not found with id: " + id);
        }

        List<ProductImageEntity> images = productImageRepository.findByProductIdAndDeletedAtIsNull(id);
        List<ProductVariantEntity> variants = productVariantRepository.findByProductIdAndDeletedAtIsNull(id);

        return productMapper.toDetailDto(product, images, variants);
    }

    /**
     * Get product by slug
     */
    @Transactional(readOnly = true)
    public ProductDetailDto getBySlug(String slug) {
        ProductEntity product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Product not found with slug: " + slug));

        if (product.getDeletedAt() != null) {
            throw new NotFoundException("Product not found with slug: " + slug);
        }

        List<ProductImageEntity> images = productImageRepository.findByProductIdAndDeletedAtIsNull(product.getId());
        List<ProductVariantEntity> variants = productVariantRepository.findByProductIdAndDeletedAtIsNull(product.getId());

        return productMapper.toDetailDto(product, images, variants);
    }

    /**
     * Create a new product
     */
    public ProductDetailDto create(ProductDetailDto dto) {
        // Check if slug already exists
        if (productRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new BadRequestException("Product with slug '" + dto.getSlug() + "' already exists");
        }

        ProductEntity entity = ProductEntity.builder()
                .name(dto.getName())
                .slug(dto.getSlug())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : ProductStatus.ACTIVE)
                .build();

        ProductEntity saved = productRepository.save(entity);

        return productMapper.toDetailDto(saved, List.of(), List.of());
    }

    /**
     * Update a product
     */
    public ProductDetailDto update(Long id, ProductDetailDto dto) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        if (product.getDeletedAt() != null) {
            throw new NotFoundException("Product not found with id: " + id);
        }

        // Check if slug already exists (and it's not the same product)
        if (!product.getSlug().equals(dto.getSlug()) &&
            productRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new BadRequestException("Product with slug '" + dto.getSlug() + "' already exists");
        }

        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());

        ProductEntity updated = productRepository.save(product);

        List<ProductImageEntity> images = productImageRepository.findByProductIdAndDeletedAtIsNull(id);
        List<ProductVariantEntity> variants = productVariantRepository.findByProductIdAndDeletedAtIsNull(id);

        return productMapper.toDetailDto(updated, images, variants);
    }

    /**
     * Delete a product (soft delete)
     */
    public void delete(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));

        if (product.getDeletedAt() != null) {
            throw new NotFoundException("Product not found with id: " + id);
        }

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    /**
     * Get low stock variants for a product
     */
    @Transactional(readOnly = true)
    public List<ProductVariantEntity> getLowStockVariants(Long productId) {
        // This would need a more complex query implementation
        List<ProductVariantEntity> variants = productVariantRepository.findByProductIdAndDeletedAtIsNull(productId);
        return variants.stream()
                .filter(v -> v.getStockQty() < v.getLowStockThreshold())
                .toList();
    }
}


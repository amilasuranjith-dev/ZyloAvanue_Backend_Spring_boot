# Project Architecture Documentation

## Layered Architecture Overview

This project has been restructured to follow a **Layered Architecture** pattern, which promotes separation of concerns, scalability, and maintainability.

## Architecture Layers

### 1. **Presentation Layer (Controllers)**
- **Location**: `feature/*/controller/`
- **Responsibility**: Handle HTTP requests and responses
- **Examples**: 
  - `feature.auth.controller.AuthController`
  - `feature.catalog.product.controller.ProductController`
  - `feature.order.controller.OrderController`

### 2. **Business Logic Layer (Services)**
- **Location**: `feature/*/service/`
- **Responsibility**: Contain business rules and orchestration logic
- **Examples**:
  - `feature.catalog.product.service.ProductService`
  - `feature.order.service.OrderService`
  - `feature.inventory.service.InventoryMovementService`

### 3. **Data Access Layer (Repositories)**
- **Location**: `feature/*/repository/`
- **Responsibility**: Abstract data access operations from the database
- **Examples**:
  - `feature.catalog.product.repository.ProductRepository`
  - `feature.order.repository.OrderRepository`
  - `feature.catalog.category.repository.CategoryRepository`

### 4. **Domain Layer (Entities)**
- **Location**: `feature/*/entity/`
- **Responsibility**: Represent database models and business entities
- **Examples**:
  - `feature.catalog.product.entity.ProductEntity`
  - `feature.order.entity.OrderEntity`
  - `feature.user.entity.UserEntity`

### 5. **Data Transfer Objects (DTOs)**
- **Location**: `feature/*/dto/`
- **Responsibility**: Transfer data between layers, especially between API and services
- **Examples**:
  - `feature.catalog.product.dto.ProductDetailDto`
  - `feature.order.dto.OrderCreateRequest`
  - `feature.payment.dto.PaymentResponseDto`

### 6. **Cross-Cutting Concerns Layer**
- **Location**: `common/` and `config/`
- **Responsibility**: Handle concerns that span multiple layers
  - **Exception Handling**: `common/exception/` - Global exception handling and custom exceptions
  - **API Response**: `common/api/` - Standardized API response format
  - **Configuration**: `config/` - Spring configuration, security config, environment config
  - **Security**: `config/security/` - JWT, authentication, authorization

## Project Structure

```
com/zyloavenue/api/
├── ZyloAvenueApiApplication.java          # Application entry point
│
├── common/                                 # Cross-cutting concerns
│   ├── api/                                # API utilities
│   │   └── ApiResponse.java                # Standard API response wrapper
│   ├── exception/                          # Exception handling
│   │   ├── ApiError.java
│   │   ├── BadRequestException.java
│   │   ├── NotFoundException.java
│   │   └── GlobalExceptionHandler.java     # Global exception handler
│   ├── dto/                                # Shared DTOs
│   └── util/                               # Utility classes (empty, for future use)
│
├── config/                                 # Configuration
│   ├── EnvConfig.java                      # Environment variables configuration
│   └── security/                           # Security configuration
│       ├── SecurityConfig.java
│       ├── JwtService.java                 # JWT token generation/validation
│       ├── JwtProperties.java
│       ├── JwtAuthenticationFilter.java
│       ├── CustomUserDetailsService.java
│       ├── UserPrincipal.java
│       └── CorsProperties.java
│
├── feature/                                # Business features
│   │
│   ├── auth/                               # Authentication feature
│   │   ├── controller/
│   │   │   └── AuthController.java
│   │   ├── service/                        # (Service tier, add as needed)
│   │   └── dto/
│   │       ├── LoginRequest.java
│   │       ├── RefreshRequest.java
│   │       └── TokenPairResponse.java
│   │
│   ├── catalog/                            # Catalog module
│   │   ├── category/                       # Product categories
│   │   │   ├── controller/
│   │   │   │   └── CategoryController.java
│   │   │   ├── service/
│   │   │   │   └── CategoryService.java
│   │   │   ├── repository/
│   │   │   │   └── CategoryRepository.java
│   │   │   ├── entity/
│   │   │   │   └── CategoryEntity.java
│   │   │   ├── dto/
│   │   │   │   └── CategoryDto.java
│   │   │   └── mapper/                     # (DTO mappers, add as needed)
│   │   │
│   │   └── product/                        # Product catalog
│   │       ├── controller/
│   │       │   └── ProductController.java
│   │       ├── service/
│   │       │   └── ProductService.java
│   │       ├── repository/
│   │       │   ├── ProductRepository.java
│   │       │   ├── ProductImageRepository.java
│   │       │   └── ProductVariantRepository.java
│   │       ├── entity/
│   │       │   ├── ProductEntity.java
│   │       │   ├── ProductImageEntity.java
│   │       │   ├── ProductVariantEntity.java
│   │       │   └── ProductStatus.java
│   │       ├── dto/
│   │       │   ├── ProductSummaryDto.java
│   │       │   ├── ProductDetailDto.java
│   │       │   ├── ProductImageDto.java
│   │       │   └── ProductVariantDto.java
│   │       └── mapper/                     # (DTO mappers, add as needed)
│   │
│   ├── inventory/                          # Inventory management
│   │   ├── controller/
│   │   │   └── InventoryMovementController.java
│   │   ├── service/
│   │   │   └── InventoryMovementService.java
│   │   ├── repository/
│   │   │   └── InventoryMovementRepository.java
│   │   ├── entity/
│   │   │   └── InventoryMovementEntity.java
│   │   ├── dto/
│   │   │   ├── InventoryMovementDto.java
│   │   │   └── InventoryMovementCreateRequest.java
│   │   └── mapper/                         # (DTO mappers, add as needed)
│   │
│   ├── order/                              # Order management
│   │   ├── controller/
│   │   │   └── OrderController.java
│   │   ├── service/
│   │   │   └── OrderService.java
│   │   ├── repository/
│   │   │   ├── OrderRepository.java
│   │   │   └── OrderItemRepository.java
│   │   ├── entity/
│   │   │   ├── OrderEntity.java
│   │   │   └── OrderItemEntity.java
│   │   ├── dto/
│   │   │   ├── OrderCreateRequest.java
│   │   │   ├── OrderDetailDto.java
│   │   │   ├── OrderItemDto.java
│   │   │   └── OrderResponseDto.java
│   │   └── mapper/                         # (DTO mappers, add as needed)
│   │
│   ├── payment/                            # Payment processing
│   │   ├── controller/
│   │   │   └── PaymentController.java
│   │   ├── service/
│   │   │   └── PaymentService.java
│   │   ├── repository/
│   │   │   └── PaymentRepository.java
│   │   ├── entity/
│   │   │   └── PaymentEntity.java
│   │   ├── dto/
│   │   │   ├── PaymentDto.java
│   │   │   ├── PaymentCreateRequest.java
│   │   │   └── PaymentResponseDto.java
│   │   └── mapper/                         # (DTO mappers, add as needed)
│   │
│   └── user/                               # User management
│       ├── entity/
│       │   ├── UserEntity.java
│       │   ├── RoleEntity.java
│       │   └── RoleName.java
│       ├── repository/
│       │   ├── UserRepository.java
│       │   └── RoleRepository.java
│       ├── service/                        # (Service tier, add as needed)
│       ├── controller/                     # (Controller tier, add as needed)
│       ├── dto/                            # (DTOs, add as needed)
│       └── mapper/                         # (DTO mappers, add as needed)
```

## Benefits of This Architecture

### 1. **Separation of Concerns**
- Each layer has a specific responsibility
- Easier to understand and modify code
- Changes in one layer don't affect others unnecessarily

### 2. **Testability**
- Each layer can be tested independently
- Mock dependencies easily
- Unit and integration tests are straightforward

### 3. **Scalability**
- Easy to add new features without affecting existing ones
- Clear patterns for developers to follow
- Each feature module can evolve independently

### 4. **Maintainability**
- Code is organized logically by feature
- Clear naming conventions
- Easy to locate specific functionality

### 5. **Reusability**
- Services can be reused across different controllers
- DTOs standardize data transfer
- Common utilities can be shared

## Data Flow

```
HTTP Request
    ↓
Controller (Endpoint handler)
    ↓
Service (Business logic)
    ↓
Repository (Data access)
    ↓
Database (JPA/Hibernat)
    ↓
← ← ← ← (Response path)
    ↓
Entity/DTO Mapping
    ↓
DTO (JSON response)
    ↓
HTTP Response
```

## Key Patterns Used

### 1. **Controller Pattern**
- Handles HTTP requests/responses
- Validates input using `@Valid` and `@Validated`
- Delegates to services
- Returns `ApiResponse` wrapper

### 2. **Service Pattern**
- Contains business logic
- Coordinates between repositories
- Performs calculations and validations
- Handles transactions

### 3. **Repository Pattern**
- Abstracts data access
- Provides CRUD operations
- Enables database independence
- Simplifies testing with mocks

### 4. **DTO Pattern**
- Separates API contracts from domain models
- Prevents over/under fetching of data
- Provides API versioning flexibility
- Improves security by limiting exposed data

### 5. **Global Exception Handling**
- Centralized error handling via `GlobalExceptionHandler`
- Consistent error response format
- Reduced code duplication

## Migration Notes

- ✅ All files have been successfully migrated
- ✅ Package names have been updated throughout
- ✅ Imports have been corrected
- ✅ Old structure backed up to `api_backup/`
- ✅ Project compiles successfully

## Future Improvements

1. **Add Mapper Classes**: Create mapper classes in `mapper/` directories for DTO conversions
2. **Add Service Layer to Auth**: Move authentication logic to a service
3. **Add User Controller**: Implement user management endpoints
4. **Add Integration Tests**: Test feature modules end-to-end
5. **Add Logging**: Implement comprehensive logging across all layers
6. **API Versioning**: Consider implementing API versioning strategy

## Best Practices

1. ✅ **One Responsibility**: Each class should have one primary responsibility
2. ✅ **Dependency Injection**: Use constructor injection for dependencies
3. ✅ **Immutability**: Use `@Getter`, `@Setter` for DTOs
4. ✅ **Documentation**: Keep this document updated as architecture evolves
5. ✅ **Consistency**: Follow the established naming patterns
6. ✅ **No Cross-Feature Imports**: Features should not directly depend on each other

## Getting Help

- Refer to this document for architecture questions
- Check Spring Boot documentation for framework specifics
- Review similar feature implementations for patterns


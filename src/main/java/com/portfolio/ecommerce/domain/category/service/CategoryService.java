package com.portfolio.ecommerce.domain.category.service;

import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.CategoryExceptionCode;
import com.portfolio.ecommerce.common.exception.code.ProductExceptionCode;
import com.portfolio.ecommerce.domain.category.dto.CategoryResponse;
import com.portfolio.ecommerce.domain.category.entity.Category;
import com.portfolio.ecommerce.domain.category.mapper.CategoryMapper;
import com.portfolio.ecommerce.domain.category.repository.CategoryRepository;
import com.portfolio.ecommerce.domain.category.dto.CategoryRequest;
import com.portfolio.ecommerce.domain.category.dto.CategoryUpdateRequest;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  public CategoryResponse create(CategoryRequest request) {
    Category parent = categoryRepository.findById(request.getParent())
        .orElse(null);

    Category category = Category.builder()
        .name(request.getName())
        .parent(parent)
        .description(request.getDescription())
        .build();

    categoryRepository.save(category);
    return CategoryMapper.INSTANCE.toCategoryResponse(category);
  }

  public List<CategoryResponse> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return CategoryMapper.INSTANCE.toCategoryResponseList(categories);
  }

  public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ServiceException(
        CategoryExceptionCode.NOT_FOUND_CATEGORY));

    category.setName(request.getName());
    category.setDescription(request.getDescription());

    categoryRepository.save(category);
    return CategoryMapper.INSTANCE.toCategoryResponse(category);
  }

  @Transactional
  public void deleteCategory(Long id) {
    Category category = categoryRepository.findById(id).orElseThrow(() -> new ServiceException(
        CategoryExceptionCode.NOT_FOUND_CATEGORY));

    if (!category.getCategories().isEmpty() || !category.getProducts().isEmpty()) {
      throw new ServiceException(CategoryExceptionCode.CATEGORY_NOT_EMPTY);
    }

    categoryRepository.delete(category);
  }

  public CategoryResponse getProductCategory(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ServiceException(
        ProductExceptionCode.NOT_FOUND_PRODUCT));
    return CategoryMapper.INSTANCE.toCategoryResponse(product.getCategory());
  }
}

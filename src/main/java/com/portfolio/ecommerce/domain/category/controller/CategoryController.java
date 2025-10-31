package com.portfolio.ecommerce.domain.category.controller;

import com.portfolio.ecommerce.common.response.ApiResponse;
import com.portfolio.ecommerce.domain.category.dto.CategoryResponse;
import com.portfolio.ecommerce.domain.category.service.CategoryService;
import com.portfolio.ecommerce.domain.category.dto.CategoryRequest;
import com.portfolio.ecommerce.domain.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
    return ApiResponse.success(categoryService.create(request));
  }

  @GetMapping
  public ApiResponse<List<CategoryResponse>> getCategoryAll() {
    return ApiResponse.success(categoryService.getAllCategories());
  }

  @GetMapping("product/{productId}")
  public ApiResponse<CategoryResponse> getProductCategory(
      @PathVariable("productId") Long productId) {
    return ApiResponse.success(categoryService.getProductCategory(productId));
  }

  @PutMapping("{id}")
  public ApiResponse<CategoryResponse> updateCategory(@PathVariable("id") Long id, @RequestBody
  CategoryUpdateRequest request) {
    return ApiResponse.success(categoryService.updateCategory(id, request));
  }

  @DeleteMapping("{id}")
  public ApiResponse<String> deleteCategory(@PathVariable("id") Long id) {
    categoryService.deleteCategory(id);
    return ApiResponse.success("삭제 성공했습니다.");
  }
}

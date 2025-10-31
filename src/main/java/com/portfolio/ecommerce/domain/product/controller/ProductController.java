package com.portfolio.ecommerce.domain.product.controller;

import com.portfolio.ecommerce.common.response.ApiResponse;
import com.portfolio.ecommerce.domain.product.dto.ProductRequest;
import com.portfolio.ecommerce.domain.product.dto.ProductResponse;
import com.portfolio.ecommerce.domain.product.dto.ProductSearchRequest;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.service.ProductService;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
    ProductResponse product = productService.create(request);
    return ApiResponse.success(product);
  }

  @GetMapping("{id}")
  public ApiResponse<ProductResponse> getProduct(@PathVariable("id") Long id) {
    ProductResponse product = productService.getProduct(id);
    return ApiResponse.success(product);
  }


  @GetMapping("/categoy/{id}")
  public ApiResponse<List<ProductResponse>> searchWithCategory(@PathVariable("id") Long category) {
    return ApiResponse.success(productService.getProductsByCategory(category));
  }

  @GetMapping("/price/")
  public ApiResponse<List<ProductResponse>> searchWithPrice(
      @RequestParam("minPrice") BigDecimal minPrice,
      @RequestParam("maxPrice") BigDecimal maxPrice) {
    return ApiResponse.success(productService.getProductsByPriceBetween(minPrice, maxPrice));
  }

  @GetMapping("/name/{name}")
  public ApiResponse<List<ProductResponse>> searchWithName(@PathVariable("name") String name) {
    return ApiResponse.success(productService.getProductsByName(name));
  }

  @GetMapping()
  public ApiResponse<List<ProductResponse>> getAll() {
    return ApiResponse.success(productService.getAllProducts());
  }

  @PutMapping("{id}")
  public ApiResponse<Product> updateProduct(@PathVariable("id") Long id,
      @RequestBody ProductRequest request) {
    Product product = productService.updateProduct(id, request);
    return ApiResponse.success(product);
  }

  @DeleteMapping("{id}")
  public ApiResponse<String> deleteProduct(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return ApiResponse.success("상품 삭제 성공했습니다.");
  }

  @GetMapping("search")
  public ApiResponse<List<ProductResponse>> search(@ModelAttribute ProductSearchRequest request) {
    return ApiResponse.success(productService.search(request));
  }
}

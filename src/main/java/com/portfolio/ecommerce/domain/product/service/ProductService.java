package com.portfolio.ecommerce.domain.product.service;

import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.CategoryExceptionCode;
import com.portfolio.ecommerce.common.exception.code.ProductExceptionCode;
import com.portfolio.ecommerce.domain.category.entity.Category;
import com.portfolio.ecommerce.domain.category.repository.CategoryRepository;
import com.portfolio.ecommerce.domain.product.dto.ProductRequest;
import com.portfolio.ecommerce.domain.product.dto.ProductResponse;
import com.portfolio.ecommerce.domain.product.dto.ProductSearchRequest;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.mapper.ProductMapper;
import com.portfolio.ecommerce.domain.product.repository.ProductQueryRepository;
import com.portfolio.ecommerce.domain.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductQueryRepository productQueryRepository;

  public ProductResponse create(ProductRequest request) {
    Category category = categoryRepository.findById(request.getCategory_id())
        .orElseThrow(() -> new ServiceException(CategoryExceptionCode.NOT_FOUND_CATEGORY));

    Product product = Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .category(category)
        .price(request.getPrice())
        .stock(request.getStock())
        .build();

    productRepository.save(product);
    return ProductMapper.INSTANCE.toResponse(product);
  }

  public ProductResponse getProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ServiceException(ProductExceptionCode.NOT_FOUND_PRODUCT));
    return ProductMapper.INSTANCE.toResponse(product);
  }

  public Product updateProduct(Long id, ProductRequest request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ServiceException(ProductExceptionCode.NOT_FOUND_PRODUCT));

    if (StringUtils.hasText(request.getName())) {
      product.setName(request.getName());
    }
    if (StringUtils.hasText(request.getDescription())) {
      product.setDescription(request.getDescription());
    }
    if (request.getPrice() != null) {
      product.setPrice(request.getPrice());
    }
    if (request.getStock() != null) {
      product.setStock(request.getStock());
    }

    if (request.getCategory_id() != null) {
      Category category = categoryRepository.findById(request.getCategory_id())
          .orElseThrow(() -> new ServiceException(CategoryExceptionCode.NOT_FOUND_CATEGORY));
      product.setCategory(category);
    }
    productRepository.save(product);
    return product;
  }

  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ServiceException(ProductExceptionCode.NOT_FOUND_PRODUCT));
    productRepository.delete(product);
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return ProductMapper.INSTANCE.toListResponse(products);
  }

  public List<ProductResponse> getProductsByCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ServiceException(CategoryExceptionCode.NOT_FOUND_CATEGORY));
    List<Product> products = productRepository.findByCategory(category);
    return ProductMapper.INSTANCE.toListResponse(products);
  }

  public List<ProductResponse> getProductsByName(String name) {
    List<Product> products = productRepository.findByName(name);
    return ProductMapper.INSTANCE.toListResponse(products);
  }

  public List<ProductResponse> getProductsByPriceBetween(BigDecimal min, BigDecimal max) {
    List<Product> products = productRepository.findByPriceBetween(min, max);
    return ProductMapper.INSTANCE.toListResponse(products);
  }

  public List<ProductResponse> search(ProductSearchRequest request) {
    Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);
    return ProductMapper.INSTANCE.toListResponse(productQueryRepository.search(category, request));
  }
}

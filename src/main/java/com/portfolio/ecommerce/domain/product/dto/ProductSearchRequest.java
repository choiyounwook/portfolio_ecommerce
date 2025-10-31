package com.portfolio.ecommerce.domain.product.dto;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSearchRequest {

  Long categoryId;
  BigDecimal minPrice;
  BigDecimal maxPrice;
  BigDecimal minStock;
  String name;
  String sortOrder;
}

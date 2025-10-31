package com.portfolio.ecommerce.domain.category.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryUpdateRequest {

  String name;
  String description;
}

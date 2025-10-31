package com.portfolio.ecommerce.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {

  @NotBlank(message = "name은 필수값입니다.")
  String name;
  Long parent;
  String description;
}

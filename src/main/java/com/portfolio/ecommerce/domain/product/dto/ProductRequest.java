package com.portfolio.ecommerce.domain.product.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

  @NotBlank(message = "name은 필수 값입니다.")
  String name;
  @NotNull(message = "price는 필수 값입니다.")
  @Digits(integer = 10, fraction = 2)
  BigDecimal price;
  @NotNull(message = "stock은 필수 값입니다.")
  Integer stock;
  @NotNull(message = "category_id는 필수 값입니다.")
  Long category_id;

  String description;
}

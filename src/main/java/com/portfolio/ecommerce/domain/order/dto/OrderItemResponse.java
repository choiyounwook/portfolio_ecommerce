package com.portfolio.ecommerce.domain.order.dto;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {

  String name;
  int quantity;
  BigDecimal price;

}

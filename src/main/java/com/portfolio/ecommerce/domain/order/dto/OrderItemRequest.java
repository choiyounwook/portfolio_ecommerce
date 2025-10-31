package com.portfolio.ecommerce.domain.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemRequest {

  @NotNull(message = "상품 정보는 필수값입니다.")
  Long product_id;
  @NotNull(message = "구매하실 수량은 필수값입니다.")
  Integer quantity;
}

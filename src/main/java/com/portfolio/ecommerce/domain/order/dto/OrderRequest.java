package com.portfolio.ecommerce.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

  @NotNull(message = "사용자 정보는 필수값입니다.")
  Long user_id;
  List<OrderItemRequest> orderItems;
  @NotBlank(message = "주소는 필수값입니다.")
  String address;
}

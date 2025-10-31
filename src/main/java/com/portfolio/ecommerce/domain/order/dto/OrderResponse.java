package com.portfolio.ecommerce.domain.order.dto;

import com.portfolio.ecommerce.common.constants.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

  OrderStatus status;
  String address;
  BigDecimal totalPrice;
  LocalDateTime createdAt;
  List<OrderItemResponse> orderItems;

  @Builder
  public OrderResponse(OrderStatus status,
      String address,
      BigDecimal totalPrice,
      LocalDateTime createdAt,
      List<OrderItemResponse> orderItems) {
    this.status = status;
    this.address = address;
    this.totalPrice = totalPrice;
    this.createdAt = createdAt;
    this.orderItems = orderItems;
  }
}

package com.portfolio.ecommerce.domain.refund.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundOrderItemRequest {

  Long id;
  Integer refundQuantity;
}

package com.portfolio.ecommerce.common.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum RefundStatus {

  PENDING("PENDING"),
  APROVED("APROVED"),
  REJECTED("REJECTED"),
  ;
  String status;

  public String getStatus() {
    return status;
  }

  RefundStatus(String status) {
    this.status = status;
  }
}

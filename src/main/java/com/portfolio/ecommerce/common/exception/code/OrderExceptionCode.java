package com.portfolio.ecommerce.common.exception.code;

import lombok.Getter;

@Getter
public enum OrderExceptionCode {
  NOT_FOUND_ORDER("NOT_FOUND_ORDER", "주문 내역을 찾을 수 없습니다."),
  ALREADY_COMPLETED_ORDER("ALREADY_COMPLETED_ORDER", "보류 상태가 아닌 주문입니다."),
  INVALID_STATUS_ORDER("INVALID_STATUS_ORDER", "유효하지 않는 상태입니다."),
  INVALID_USER_ORDER("INVALID_USER_ORDER", "주문자 정보가 일치하지 않습니다."),
  ;
  private final String code;
  private final String message;

  OrderExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}

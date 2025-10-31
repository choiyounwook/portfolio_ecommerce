package com.portfolio.ecommerce.common.exception.code;

import lombok.Getter;

@Getter
public enum RefundExceptionCode {
  NOT_FOUND_REFUND("NOT_FOUND_REFUND", "환불 정보를 찾을 수 없습니다."),
  ALREADY_COMPLETED_REFUND("ALREADY_COMPLETED_REFUND", "보류 상태가 아닌 환불입니다."),
  INVALID_STATUS_REFUND("INVALID_STATUS_REFUND", "유효하지 않는 상태입니다."),
  ;

  private final String code;
  private final String message;

  RefundExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}

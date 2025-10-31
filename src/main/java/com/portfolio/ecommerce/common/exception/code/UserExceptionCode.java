package com.portfolio.ecommerce.common.exception.code;

import lombok.Getter;

@Getter
public enum UserExceptionCode {
  NOT_FOUND_USER("NOT_FOUND_USER", "사용자를 찾을 수 없습니다."),
  ;
  private final String code;
  private final String message;

  UserExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}

package com.portfolio.ecommerce.common.exception.code;

import lombok.Getter;

@Getter
public enum ProductExceptionCode {

  OUT_OF_STOCK("OUT_OF_STOCK", "상품 재고가 부족합니다."),
  NOT_FOUND_PRODUCT("NOT_FOUND_PRODUCT", "삼품을 찾을 수 없습니다."),
  ;

  private final String code;
  private final String message;

  ProductExceptionCode(String code,
      String message) {
    this.code = code;
    this.message = message;
  }
}

package com.portfolio.ecommerce.common.exception.code;

import lombok.Getter;

@Getter
public enum CategoryExceptionCode {

  NOT_FOUND_CATEGORY("NOT_FOUND_CATEGORY", "카테고리를 찾을 수 없습니다."),

  CHILD_CATEGORY_EXISTS("CHILD_CATEGORY_EXISTS", "자식 카테고리가 존재합니다."),
  CATEGORY_NOT_EMPTY("CATEGORY_NOT_EMPTY", "카테고리에 삼품이 존재합니다."),
  ;
  private final String code;
  private final String message;

  CategoryExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}

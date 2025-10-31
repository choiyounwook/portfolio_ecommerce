package com.portfolio.ecommerce.common.exception;

import com.portfolio.ecommerce.common.exception.code.CategoryExceptionCode;
import com.portfolio.ecommerce.common.exception.code.OrderExceptionCode;
import com.portfolio.ecommerce.common.exception.code.ProductExceptionCode;
import com.portfolio.ecommerce.common.exception.code.RefundExceptionCode;
import com.portfolio.ecommerce.common.exception.code.UserExceptionCode;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

  private String code;
  private String message;

  public ServiceException(CategoryExceptionCode response) {
    super(response.getMessage());
    this.code = response.getCode();
    this.message = response.getMessage();
  }

  public ServiceException(OrderExceptionCode response) {
    super(response.getMessage());
    this.code = response.getCode();
    this.message = response.getMessage();
  }

  public ServiceException(ProductExceptionCode response) {
    super(response.getMessage());
    this.code = response.getCode();
    this.message = response.getMessage();
  }

  public ServiceException(UserExceptionCode response) {
    super(response.getMessage());
    this.code = response.getCode();
    this.message = response.getMessage();
  }

  public ServiceException(RefundExceptionCode response) {
    super(response.getMessage());
    this.code = response.getCode();
    this.message = response.getMessage();
  }
}

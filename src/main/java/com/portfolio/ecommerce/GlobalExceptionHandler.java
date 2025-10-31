package com.portfolio.ecommerce;

import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<?> handleResponseException(ServiceException exception) {
    return ApiResponse.ResponseException(exception.getCode(), exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> methodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    AtomicReference<String> errors = new AtomicReference<>("");
    exception.getBindingResult().getAllErrors().forEach(c -> errors.set(c.getDefaultMessage()));

    return ApiResponse.ValidException("VALIDATE_ERROR", String.valueOf(errors));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<?> bindException(BindException exception) {
    AtomicReference<String> errors = new AtomicReference<>("");
    exception.getBindingResult().getAllErrors().forEach(c -> errors.set(c.getDefaultMessage()));
    return ApiResponse.ValidException("VALIDATE_ERROR", String.valueOf(errors));

  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<?> handleException(Exception exception) {
    return ApiResponse.ServerException("SERVER_ERROR", exception.getMessage());
  }
}

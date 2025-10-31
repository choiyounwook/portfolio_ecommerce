package com.portfolio.ecommerce.domain.order.controller;

import com.portfolio.ecommerce.common.response.ApiResponse;
import com.portfolio.ecommerce.domain.order.dto.OrderRequest;
import com.portfolio.ecommerce.domain.order.service.OrderService;
import com.portfolio.ecommerce.domain.order.dto.OrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ApiResponse<String> create(@Valid @RequestBody OrderRequest request) {
    boolean success = orderService.create(request);
    if (success) {
      return ApiResponse.success("주문이 정상 처리됐습니다.");
    }
    return ApiResponse.fail("주문 처리가 실패했습니다.");

  }

  @GetMapping("/user/{id}")
  public ApiResponse<List<OrderResponse>> findByUser(@PathVariable("id") Long id) {
    return ApiResponse.success(orderService.findBuUser(id));
  }

  @PutMapping("{id}")
  public ApiResponse<OrderResponse> updateOrder(@PathVariable("id") Long id,
      @NotBlank(message = "상태 정보는 필수입니다.") @RequestParam String status) {
    return ApiResponse.success(orderService.updateStatus(id, status));
  }

  @PutMapping("/cencel/{id}")
  public ApiResponse<OrderResponse> canceledOrder(@PathVariable("id") Long id) {
    return ApiResponse.success(orderService.canceledOrder(id));
  }
}

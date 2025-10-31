package com.portfolio.ecommerce.domain.refund.controller;

import com.portfolio.ecommerce.common.constants.RefundStatus;
import com.portfolio.ecommerce.common.response.ApiResponse;
import com.portfolio.ecommerce.domain.message.RefundMessage;
import com.portfolio.ecommerce.domain.refund.dto.RefundRequest;
import com.portfolio.ecommerce.domain.refund.dto.RefundResponse;
import com.portfolio.ecommerce.domain.refund.service.RefundService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/refunds")
public class RefundController {

  private final RefundService refundService;

  @PostMapping
  public ApiResponse<RefundResponse> create(@Valid @RequestBody RefundRequest request) {
    return ApiResponse.success(refundService.create(request));
  }

  @PutMapping("{id}/approved")
  public ApiResponse<String> approved(@PathVariable("id") Long id) {
    boolean success = refundService.update(id, RefundStatus.APROVED);
    if (success) {
      return ApiResponse.success(RefundMessage.APPROVE_SUCCESS);
    }
    return ApiResponse.fail(RefundMessage.APPROVE_FAIL);
  }

  @PutMapping("{id}/rejected")
  public ApiResponse<String> rejected(@PathVariable("id") Long id) {
    boolean success = refundService.update(id, RefundStatus.REJECTED);
    if (success) {
      return ApiResponse.success(RefundMessage.REJECT_SUCCESS);
    }
    return ApiResponse.fail(RefundMessage.REJECT_FAIL);

  }

  @GetMapping("user/{userId}")
  public ApiResponse<List<RefundResponse>> getUserRefunds(@PathVariable("userId") Long userId) {
    return ApiResponse.success(refundService.getUserRefunds(userId));
  }

}

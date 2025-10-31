package com.portfolio.ecommerce.domain.refund.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundRequest {

  @NotNull(message = "사용자 id 값은 필수입니다.")
  Long userId;
  @NotNull(message = "주문 id 값은 필수입니다.")
  Long orderId;
  @NotBlank(message = "환불 사유를 작성해 주세요.")
  String reason;
  @Size(min = 1, message = "환불 상품은 최소 1개 이상이어야 합니다.")
  List<RefundOrderItemRequest> orderItems;

}

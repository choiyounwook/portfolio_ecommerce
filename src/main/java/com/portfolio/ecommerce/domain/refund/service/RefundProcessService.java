package com.portfolio.ecommerce.domain.refund.service;

import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.ProductExceptionCode;
import com.portfolio.ecommerce.domain.order.entity.OrderItem;
import com.portfolio.ecommerce.domain.order.repository.OrderItemRepository;
import com.portfolio.ecommerce.domain.refund.dto.RefundOrderItemRequest;
import com.portfolio.ecommerce.domain.refund.dto.RefundRequest;
import com.portfolio.ecommerce.domain.refund.entity.Refund;
import com.portfolio.ecommerce.domain.refund.entity.RefundItem;
import com.portfolio.ecommerce.domain.refund.repository.RefundItemRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundProcessService {

  private final RefundItemRepository refundItemRepository;
  private final OrderItemRepository orderItemRepository;

  @Transactional
  public Refund processRefund(Refund refund, RefundRequest request) {
    Map<Long, Integer> refundItemMap = buildRefundItemMap(request);
    List<OrderItem> orderItems = orderItemRepository.findAllById(refundItemMap.keySet());
    List<RefundItem> refundItems = new ArrayList<>();
    BigDecimal refundTotalPrice = BigDecimal.ZERO;

    for (OrderItem orderItem : orderItems) {
      int refundQuantity = refundItemMap.get(orderItem.getId());
      validateRefundQuantity(orderItem, refundQuantity);
      orderItem.updateQuantity(orderItem.getQuantity() - refundQuantity);

      BigDecimal refundAmount = calculateRefundAmount(orderItem, refundQuantity);
      RefundItem refundItem = buildRefundItem(refund, orderItem, refundQuantity, refundAmount);

      refundItems.add(refundItem);
      refundTotalPrice = refundTotalPrice.add(refundAmount);
    }

    refundItemRepository.saveAll(refundItems);
    refund.updateTotalPrice(refundTotalPrice);
    return refund;
  }

  private Map<Long, Integer> buildRefundItemMap(RefundRequest request) {
    return request.getOrderItems().stream()
        .collect(Collectors.toMap(
            RefundOrderItemRequest::getId,
            RefundOrderItemRequest::getRefundQuantity
        ));
  }

  private void validateRefundQuantity(OrderItem orderItem, Integer refundQuantity) {
    if (orderItem.getQuantity() < refundQuantity) {
      throw new ServiceException(ProductExceptionCode.OUT_OF_STOCK);
    }
  }

  private BigDecimal calculateRefundAmount(OrderItem orderItem, Integer refundQuantity) {
    if (Objects.equals(orderItem.getQuantity(), 0)) {
      return orderItem.getPrice();
    }
    BigDecimal unitPrice = orderItem.getPrice()
        .divide(BigDecimal.valueOf(orderItem.getQuantity()), 2, BigDecimal.ROUND_HALF_UP);
    return unitPrice.multiply(BigDecimal.valueOf(refundQuantity));

  }

  private RefundItem buildRefundItem(Refund refund, OrderItem orderItem, Integer refundQuantity,
      BigDecimal refundAmount) {
    return RefundItem.builder()
        .refund(refund)
        .orderItem(orderItem)
        .quantity(refundQuantity)
        .price(refundAmount)
        .build();
  }

}

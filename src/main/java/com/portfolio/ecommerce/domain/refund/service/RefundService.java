package com.portfolio.ecommerce.domain.refund.service;

import com.portfolio.ecommerce.common.constants.RefundStatus;
import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.OrderExceptionCode;
import com.portfolio.ecommerce.common.exception.code.RefundExceptionCode;
import com.portfolio.ecommerce.common.exception.code.UserExceptionCode;
import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.order.entity.OrderItem;
import com.portfolio.ecommerce.domain.order.repository.OrderItemRepository;
import com.portfolio.ecommerce.domain.order.repository.OrderRepository;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.repository.ProductRepository;
import com.portfolio.ecommerce.domain.refund.dto.RefundRequest;
import com.portfolio.ecommerce.domain.refund.dto.RefundResponse;
import com.portfolio.ecommerce.domain.refund.entity.Refund;
import com.portfolio.ecommerce.domain.refund.mapper.RefundMapper;
import com.portfolio.ecommerce.domain.refund.repository.RefundRepository;
import com.portfolio.ecommerce.domain.user.entity.User;
import com.portfolio.ecommerce.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

  private final RefundRepository refundRepository;
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;

  private final RefundProcessService refundProcessService;


  public RefundResponse create(RefundRequest request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ServiceException(
        UserExceptionCode.NOT_FOUND_USER));
    Order order = orderRepository.findById(request.getOrderId())
        .orElseThrow(() -> new ServiceException(
            OrderExceptionCode.NOT_FOUND_ORDER));

    if (!order.getUser().equals(user)) {
      throw new ServiceException(OrderExceptionCode.INVALID_USER_ORDER);
    }

    Refund refund = buildRefund(order, request.getReason());

    try {
      approveRefund(refund, request);
    } catch (Exception e) {
      refund.updateStateFailed();
    }
    refundRepository.save(refund);
    return RefundMapper.INSTANCE.toRefundResponse(refund);
  }

  @Transactional
  public boolean update(Long id, RefundStatus status) {
    Refund refund = refundRepository.findById(id).orElseThrow(() -> new ServiceException(
        RefundExceptionCode.NOT_FOUND_REFUND));

    if (!refund.getStatus().equals(RefundStatus.PENDING)) {
      throw new ServiceException(RefundExceptionCode.ALREADY_COMPLETED_REFUND);
    }

    if (status.equals(RefundStatus.APROVED)) {
      canceledOrder(refund.getOrder());
      refund.updateStatus(RefundStatus.APROVED);
    } else if (status.equals(RefundStatus.REJECTED)) {
      refund.updateStatus(RefundStatus.REJECTED);
    } else {
      throw new ServiceException(RefundExceptionCode.INVALID_STATUS_REFUND);
    }

    refundRepository.save(refund);
    return true;
  }

  public List<RefundResponse> getUserRefunds(Long id) {
    return refundRepository.findAllByUser_Id(id).stream()
        .map(RefundMapper.INSTANCE::toRefundResponse)
        .collect(Collectors.toList());
  }

  public void canceledOrder(Order order) {
    canceledOrderItems(order);
  }

  private void canceledOrderItems(Order order) {
    List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
    for (OrderItem orderItem : orderItems) {
      Product product = orderItem.getProduct();
      product.setStock(product.getStock() + orderItem.getQuantity());
      productRepository.save(product);
    }
  }

  private Refund buildRefund(Order order, String reason) {
    return Refund.builder()
        .order(order)
        .user(order.getUser())
        .status(RefundStatus.PENDING)
        .reason(reason)
        .build();
  }

  private void approveRefund(Refund refund, RefundRequest request) {
    Refund processRefund = refundProcessService.processRefund(refund, request);
    processRefund.updateStateSuccess();
    refundRepository.save(processRefund);
  }

}

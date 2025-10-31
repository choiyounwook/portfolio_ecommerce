package com.portfolio.ecommerce.domain.order.service;

import com.portfolio.ecommerce.common.constants.OrderStatus;
import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.OrderExceptionCode;
import com.portfolio.ecommerce.common.exception.code.UserExceptionCode;
import com.portfolio.ecommerce.domain.order.dto.OrderItemResponse;
import com.portfolio.ecommerce.domain.order.dto.OrderRequest;
import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.order.entity.OrderItem;
import com.portfolio.ecommerce.domain.order.repository.OrderItemRepository;
import com.portfolio.ecommerce.domain.order.repository.OrderRepository;
import com.portfolio.ecommerce.domain.order.dto.OrderResponse;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.repository.ProductRepository;
import com.portfolio.ecommerce.domain.user.entity.User;
import com.portfolio.ecommerce.domain.user.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderProcessService orderProcessService;

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;
  private final OrderItemRepository orderItemRepository;

  @Transactional
  public boolean create(OrderRequest request) {
    Order order = save(request.getUser_id());
    List<OrderItem> orderItems = orderProcessService.createOrderItems(request, order);
    BigDecimal totalPrice = orderProcessService.calculateTotalPrice(orderItems);
    order.setTotalPrice(totalPrice);
    orderItemRepository.saveAll(orderItems);
    return true;
  }

  public List<OrderResponse> findBuUser(Long id) {

    User user = userRepository.findById(id).orElseThrow(() -> new ServiceException(
        UserExceptionCode.NOT_FOUND_USER));

    List<Order> orders = orderRepository.findByUser(user);

    List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();

    for (Order order : orders) {

      List<OrderItemResponse> itemsResponseList = getOrderItems(order.getId());
      OrderResponse orderResponse = OrderResponse.builder()
          .orderItems(itemsResponseList)
          .status(order.getStatus())
          .createdAt(order.getCreatedAt())
          .totalPrice(order.getTotalPrice())
          .address((order.getAddress()))
          .build();
      orderResponseList.add(orderResponse);
    }

    return orderResponseList;
  }

  public OrderResponse updateStatus(Long id, String status) {

    Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException(
        OrderExceptionCode.NOT_FOUND_ORDER));

    if (order.getStatus().equals(OrderStatus.COMPLETE) || order.getStatus()
        .equals(OrderStatus.CANCELED)) {
      throw new ServiceException(OrderExceptionCode.ALREADY_COMPLETED_ORDER);
    }

    if (status.equals(OrderStatus.COMPLETE)) {
      order.updateStatus(OrderStatus.COMPLETE);
    } else if (status.equals("canceled") && order.getStatus().equals("pending")) {
      canceledOrderItems(order);
      order.updateStatus(OrderStatus.CANCELED);
    } else {
      throw new ServiceException(OrderExceptionCode.INVALID_STATUS_ORDER);
    }

    orderRepository.save(order);

    List<OrderItemResponse> itemsResponseList = getOrderItems(order.getId());

    OrderResponse response = OrderResponse.builder()
        .orderItems(itemsResponseList)
        .status(order.getStatus())
        .createdAt(order.getCreatedAt())
        .totalPrice(order.getTotalPrice())
        .address((order.getAddress()))
        .build();

    return response;
  }

  public OrderResponse canceledOrder(Long id) {

    Order order = orderRepository.findById(id).orElseThrow(() -> new ServiceException(
        OrderExceptionCode.NOT_FOUND_ORDER));

    if (order.getStatus().equals(OrderStatus.PENDING)) {
      order.updateStatus(OrderStatus.CANCELED);
    } else {
      throw new ServiceException(OrderExceptionCode.ALREADY_COMPLETED_ORDER);
    }

    orderRepository.save(order);
    canceledOrderItems(order);
    List<OrderItemResponse> itemsResponseList = getOrderItems(order.getId());

    OrderResponse response = OrderResponse.builder()
        .orderItems(itemsResponseList)
        .status(order.getStatus())
        .createdAt(order.getCreatedAt())
        .totalPrice(order.getTotalPrice())
        .address((order.getAddress()))
        .build();

    return response;
  }

  private void canceledOrderItems(Order order) {
    List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
    for (OrderItem orderItem : orderItems) {
      Product product = orderItem.getProduct();
      product.setStock(product.getStock() + orderItem.getQuantity());
      productRepository.save(product);
    }

  }

  private List<OrderItemResponse> getOrderItems(Long orderId) {

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ServiceException(OrderExceptionCode.NOT_FOUND_ORDER));
    List<OrderItem> items = orderItemRepository.findByOrder(order);

    List<OrderItemResponse> itemsResponseList = new ArrayList<OrderItemResponse>();
    for (OrderItem item : items) {
      Product product = item.getProduct();
      OrderItemResponse itemsResponse = OrderItemResponse.builder()
          .name(product.getName())
          .price(item.getPrice())
          .quantity(item.getQuantity())
          .build();
      itemsResponseList.add(itemsResponse);
    }

    return itemsResponseList;
  }

  private Order save(Long userId) {
    User orderUser = getUser(userId);

    return orderRepository.save(Order.builder()
        .user(orderUser)
        .totalPrice(BigDecimal.ZERO)
        .build());
  }

  private User getUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new ServiceException(UserExceptionCode.NOT_FOUND_USER));
  }

}

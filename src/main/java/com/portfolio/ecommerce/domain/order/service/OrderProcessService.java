package com.portfolio.ecommerce.domain.order.service;

import com.portfolio.ecommerce.common.exception.ServiceException;
import com.portfolio.ecommerce.common.exception.code.ProductExceptionCode;
import com.portfolio.ecommerce.domain.order.dto.OrderItemRequest;
import com.portfolio.ecommerce.domain.order.dto.OrderRequest;
import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.order.entity.OrderItem;
import com.portfolio.ecommerce.domain.order.repository.OrderItemRepository;
import com.portfolio.ecommerce.domain.product.entity.Product;
import com.portfolio.ecommerce.domain.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProcessService {

  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;


  public BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
    return orderItems.stream()
        .map(
            orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public List<OrderItem> createOrderItems(OrderRequest request, Order order) {
    List<OrderItem> orderItems = new ArrayList<>();
    for (OrderItemRequest item : request.getOrderItems()) {
      Product product = productRepository.findById(item.getProduct_id())
          .orElseThrow(() -> new ServiceException(ProductExceptionCode.NOT_FOUND_PRODUCT));

      validateStock(item, product);
      product.reduceStock(item.getQuantity());
      orderItems.add(buildOrderItem(order, product, item));
    }
    return orderItems;
  }

  private void validateStock(OrderItemRequest request, Product product) {
    if (request.getQuantity() > product.getStock()) {
      throw new ServiceException(ProductExceptionCode.OUT_OF_STOCK);
    }
  }

  private OrderItem buildOrderItem(Order order, Product product, OrderItemRequest request) {
    return OrderItem.builder()
        .order(order)
        .price(product.getPrice())
        .product(product)
        .quantity(request.getQuantity())
        .build();
  }
}

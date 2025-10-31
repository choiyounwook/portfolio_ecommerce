package com.portfolio.ecommerce.domain.order.repository;

import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.order.entity.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  List<OrderItem> findByOrder(Order order);
}

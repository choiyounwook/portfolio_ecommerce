package com.portfolio.ecommerce.domain.order.repository;

import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByUser(User user);
}

package com.portfolio.ecommerce.domain.refund.repository;

import com.portfolio.ecommerce.domain.refund.entity.RefundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundItemRepository extends JpaRepository<RefundItem, Integer> {

}

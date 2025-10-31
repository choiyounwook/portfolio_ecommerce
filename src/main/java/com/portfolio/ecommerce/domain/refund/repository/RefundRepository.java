package com.portfolio.ecommerce.domain.refund.repository;

import com.portfolio.ecommerce.domain.refund.entity.Refund;
import com.portfolio.ecommerce.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

  List<Refund> findByUser(User user);

  List<Refund> findAllByUser_Id(Long userId);
}

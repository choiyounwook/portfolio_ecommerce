package com.portfolio.ecommerce.domain.refund.entity;

import com.portfolio.ecommerce.common.constants.RefundStatus;
import com.portfolio.ecommerce.domain.order.entity.Order;
import com.portfolio.ecommerce.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "refunds")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  Order order;

  @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
  BigDecimal totalPrice;

  @Column(nullable = false, columnDefinition = "TEXT")
  String reason;

  @Column(nullable = false, length = 10)
  @ColumnDefault("PENDING")
  @Enumerated(EnumType.STRING)
  RefundStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Builder
  public Refund(User user,
      Order order,
      String reason,
      RefundStatus status,
      BigDecimal totalPrice) {
    this.user = user;
    this.order = order;
    this.reason = reason;
    this.status = status;
    this.totalPrice = totalPrice;
  }

  public void updateStatus(RefundStatus status) {
    this.status = status;
  }

  public void updateTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void updateStateFailed() {
    this.status = RefundStatus.REJECTED;
  }

  public void updateStateSuccess() {
    this.status = RefundStatus.APROVED;
  }

}

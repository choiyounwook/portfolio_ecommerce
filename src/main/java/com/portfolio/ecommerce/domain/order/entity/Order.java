package com.portfolio.ecommerce.domain.order.entity;

import com.portfolio.ecommerce.common.constants.OrderStatus;
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
@Table(name = "orders")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  User user;

  @Column(name = "shipping_address", nullable = false)
  String address;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  @ColumnDefault("PENDING")
  OrderStatus status;

  @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
  BigDecimal totalPrice;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  LocalDateTime updatedAt;

  public void updateTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void updateStatus(OrderStatus status) {
    this.status = status;
  }

  @Builder
  public Order(User user,
      String address,
      BigDecimal totalPrice,
      OrderStatus status) {
    this.user = user;
    this.address = address;
    this.totalPrice = totalPrice;
    this.status = status;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

}

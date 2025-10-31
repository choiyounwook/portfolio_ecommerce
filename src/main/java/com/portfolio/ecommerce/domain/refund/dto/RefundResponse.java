package com.portfolio.ecommerce.domain.refund.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundResponse {

  Long user;
  Long order;
  String reason;
  String status;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-mm-dd")
  LocalDateTime createdAt;
}

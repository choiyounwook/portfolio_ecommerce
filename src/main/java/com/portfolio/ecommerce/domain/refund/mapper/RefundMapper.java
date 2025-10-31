package com.portfolio.ecommerce.domain.refund.mapper;

import com.portfolio.ecommerce.domain.refund.dto.RefundResponse;
import com.portfolio.ecommerce.domain.refund.entity.Refund;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RefundMapper {

  RefundMapper INSTANCE = Mappers.getMapper(RefundMapper.class);

  @Mapping(source = "user.id", target = "user")
  @Mapping(source = "order.id", target = "order")
  RefundResponse toRefundResponse(Refund refund);

  List<RefundResponse> toRefundResponseList(List<Refund> refunds);
}

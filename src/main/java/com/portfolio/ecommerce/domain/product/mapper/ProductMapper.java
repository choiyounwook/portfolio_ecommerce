package com.portfolio.ecommerce.domain.product.mapper;

import com.portfolio.ecommerce.domain.product.dto.ProductResponse;
import com.portfolio.ecommerce.domain.product.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductResponse toResponse(Product product);

  List<ProductResponse> toListResponse(List<Product> products);
}

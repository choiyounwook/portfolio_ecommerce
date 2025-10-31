package com.portfolio.ecommerce.domain.product.repository;

import static com.portfolio.ecommerce.domain.category.entity.QCategory.category;
import static com.portfolio.ecommerce.domain.product.entity.QProduct.product;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.portfolio.ecommerce.domain.category.entity.Category;
import com.portfolio.ecommerce.domain.product.dto.ProductSearchRequest;
import com.portfolio.ecommerce.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

  private final JPAQueryFactory queryFactory;

  public List<Product> search(Category requestCategory, ProductSearchRequest request) {
    return queryFactory.select(product)
        .from(product)
        .innerJoin(category)
        .on(category.eq(product.category))
        .where(
            isCategory(requestCategory),
            betweenPrice(request.getMinPrice(), request.getMaxPrice()),
            likeProductName(request.getName()),
            minStockThreshold(request.getMinStock())
        )
        .orderBy(orderByPrice(request.getSortOrder()))
        .fetch();
  }

  private BooleanExpression isCategory(Category category) {
    return Objects.isNull(category) ? null : product.category.eq(category);
  }

  private BooleanExpression betweenPrice(BigDecimal minPrice, BigDecimal maxPrice) {
    return Objects.isNull(minPrice) && Objects.isNull(maxPrice) ? null
        : product.price.between(minPrice, maxPrice);
  }

  private BooleanExpression likeProductName(String name) {
    return StringUtils.hasText(name) ? product.name.contains(name) : null;
  }

  private BooleanExpression minStockThreshold(BigDecimal minStock) {
    return Objects.isNull(minStock) ? null : product.stock.goe(minStock);
  }

  private OrderSpecifier<?> orderByPrice(String sortOrder) {
    if (StringUtils.hasText(sortOrder)) {
      if (sortOrder.toLowerCase().equals("desc")) {
        return product.price.desc();
      } else if (sortOrder.toLowerCase().equals("asc")) {
        return product.price.asc();
      }
    }
    return product.id.desc();
  }

}

package com.portfolio.ecommerce.domain.product.repository;

import com.portfolio.ecommerce.domain.category.entity.Category;
import com.portfolio.ecommerce.domain.product.entity.Product;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByCategory(Category category);

  List<Product> findByName(String name);

  List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}

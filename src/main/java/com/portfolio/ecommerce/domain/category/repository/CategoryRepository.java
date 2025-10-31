package com.portfolio.ecommerce.domain.category.repository;

import com.portfolio.ecommerce.domain.category.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByParent(Category parent);
}

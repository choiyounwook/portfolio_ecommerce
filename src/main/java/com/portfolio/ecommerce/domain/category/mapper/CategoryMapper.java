package com.portfolio.ecommerce.domain.category.mapper;

import com.portfolio.ecommerce.domain.category.dto.CategoryResponse;
import com.portfolio.ecommerce.domain.category.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  CategoryResponse toCategoryResponse(Category category);

  List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}

package com.zwj.mapper;

import com.zwj.controller.request.CategoryPageRequest;
import com.zwj.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    List<Category> list();

    List<Category> listByCondition(CategoryPageRequest categoryPageRequest);

    int save(Category category);

    int save2(Category category);

    int update(Category category);

    int delete(int id);

    Category getCategoryByName(String name);

}

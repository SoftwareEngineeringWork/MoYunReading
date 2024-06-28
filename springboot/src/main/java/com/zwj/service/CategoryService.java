package com.zwj.service;

import com.zwj.controller.request.CategoryPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Category;
import com.zwj.pojo.User;

import java.util.List;

public interface CategoryService {

    List<Category> list();

    Object page(CategoryPageRequest categoryPageRequest);

    int save(Category category);

    int save2(Category category);

    int update(Category category);

    int delete(int id);

    Category getCategoryByName(String name);
}

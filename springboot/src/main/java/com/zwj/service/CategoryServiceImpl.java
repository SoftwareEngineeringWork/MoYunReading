package com.zwj.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.CategoryPageRequest;
import com.zwj.mapper.CategoryMapper;
import com.zwj.pojo.Category;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }

    @Override
    public Object page(CategoryPageRequest categoryPageRequest) {
        PageHelper.startPage(categoryPageRequest.getPageNum(),categoryPageRequest.getPageSize());
        List<Category> categories = categoryMapper.listByCondition(categoryPageRequest);
        return new PageInfo<>(categories);
    }

    @Override
    public int save(Category category) {
        return categoryMapper.save(category);
    }

    @Override
    public int save2(Category category) {
        return categoryMapper.save2(category);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public int delete(int id) {
        return categoryMapper.delete(id);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryMapper.getCategoryByName(name);
    }


}

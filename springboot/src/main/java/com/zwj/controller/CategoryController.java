package com.zwj.controller;

import cn.hutool.core.collection.CollUtil;
import com.zwj.controller.request.CategoryPageRequest;
import com.zwj.pojo.Category;
import com.zwj.pojo.User;
import com.zwj.service.CategoryServiceImpl;
import com.zwj.service.UserServiceImpl;
import com.zwj.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryServiceImpl categoryService;

    @PostMapping("/add")
    public Result<?> save(@RequestBody Category category){
        if (categoryService.getCategoryByName(category.getName()) != null){
            return Result.error("添加失败，该分类名已存在！");
        }
        int result = categoryService.save(category);
        if (result == 1){
            return Result.success();
        }
        return Result.error("未知错误！");
    }

    @PostMapping("/add2")
    public Result<?> save2(@RequestBody Category category){
        if (categoryService.getCategoryByName(category.getName()) != null){
            return Result.error("添加失败，该分类名已存在！");
        }
        int result = categoryService.save2(category);
        if (result == 1){
            return Result.success();
        }
        return Result.error("未知错误！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int result = categoryService.delete(id);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败");
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Category category){
        if (categoryService.getCategoryByName(category.getName()).getId() != category.getId()){
            return Result.error("编辑失败，该分类名已存在！");
        }
        category.setUpdateTime(new Date());
        int result = categoryService.update(category);
        if (result == 1){
            return Result.success();
        }
        return Result.error("修改失败！");
    }


    @GetMapping("/list")
    public Result<?> list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    @GetMapping("/tree")
    public Result<?> tree(){
        List<Category> list = categoryService.list();
        return Result.success(createTree(null, list));   //  null 表示从第一级开始递归
    }

    @GetMapping("/page")
    public Result<?> page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

    // 完全递归的方法来实现递归树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> treeList = new ArrayList<>();
        for (Category category : categories) {
            if (pid == null) {
                if (category.getPid() == null) {  // 那这就是第一级节点
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            } else {
                if (String.valueOf(pid).equals(category.getPid())) {
                    treeList.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if (CollUtil.isEmpty(category.getChildren())) {
                category.setChildren(null);
            }
        }
        return treeList;
    }

}

package com.zwj.controller;

import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Book;
import com.zwj.pojo.User;
import com.zwj.service.UserServiceImpl;
import com.zwj.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @PostMapping("/add")
    public Result<?> save(@RequestBody User user){
        int result = userService.save(user);
        if (result == 1){
            return Result.success();
        }
        return Result.error("未知错误！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int result = userService.delete(id);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败！请检查此用户所有交易是否完成");
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody User user){
        user.setUpdatetime(new Date());
        int result = userService.updateAccount(user);
        if (result == 1){
            return Result.success();
        }
        return Result.error("修改失败！");
    }

    @PostMapping("/recharge")
    public Result<?> updateAccount(@RequestBody User user){
        user.setUpdatetime(new Date());
        user.setAccount(user.getAccount() + user.getMoney());
        int result = userService.updateAccount(user);
        if (result == 1){
            return Result.success();
        }
        return Result.error("修改失败！");
    }


    @GetMapping("/list")
    public Result<?> list(){
        List<User> users = userService.list();
        return Result.success(users);
    }

    @GetMapping("/{id}")
    public Result<?> find(@PathVariable Integer id){
        User user = userService.findUserById(id);
        return Result.success(user);
    }

    @GetMapping("/page")
    public Result<?> page(UserPageRequest userPageRequest){
        return Result.success(userService.page(userPageRequest));
    }

}

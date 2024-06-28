package com.zwj.controller;

import cn.hutool.core.date.DateUtil;
import com.zwj.controller.request.BorrowPageRequest;
import com.zwj.pojo.Borrow;
import com.zwj.pojo.User;
import com.zwj.service.*;
import com.zwj.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    BorrowServiceImpl borrowService;

    @Resource
    UserServiceImpl userService;

    @Resource
    JavaMailSenderImpl mailSender;

    @PostMapping("/add")
    public Result<?> save(@RequestBody Borrow borrow){
        int result = borrowService.save(borrow);
        if (result == 1){
            return Result.success();
        }
        return Result.error("未知错误！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int result = borrowService.delete(id);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败！请检查此用户所有交易是否完成");
    }

    @GetMapping("/list")
    public Result<?> list(){
        List<Borrow> borrows = borrowService.list();
        return Result.success(borrows);
    }

    @GetMapping("/page")
    public Result<?> page(BorrowPageRequest borrowPageRequest){
        System.out.println(borrowPageRequest.getBookName() + borrowPageRequest.getUserName() + borrowPageRequest.getUserPhone());
        return Result.success(borrowService.page(borrowPageRequest));
    }

    //还书列表
    @GetMapping("/page2")
    public Result<?> page2(BorrowPageRequest borrowPageRequest){
        System.out.println(borrowPageRequest.getBookName() + borrowPageRequest.getUserName() + borrowPageRequest.getUserPhone());
        return Result.success(borrowService.page2(borrowPageRequest));
    }

    //还书
    @PostMapping("/update")
    public Result<?> update(@RequestBody Borrow borrow){
        int result = borrowService.update(borrow);
        if (result == 1){
            return Result.success();
        }
        return Result.error("还书失败！");
    }

    @DeleteMapping("/return/{id}")
    public Result<?> deleteReturn(@PathVariable Integer id){
        Borrow borrow = borrowService.getBorrowById(id);
        int result = borrowService.deleteReturn(borrow);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败！请检查此用户所有交易是否完成");
    }

    //给用户发邮件提醒
    @PostMapping("/remind")
    public Result<?> remind(@RequestBody Borrow borrow){
        User user = userService.findUserByUsername(borrow.getUserId());
        String email = user.getEmail();
        String time = DateUtil.formatDate(borrow.getCreateTime());
        if (email != null){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("借阅图书到期提醒");
            message.setText("您于"+ time
                    + "借阅的一本《" + borrow.getBookName() + "》已经" + borrow.getDays() + "天了"
                    + "，未避免产生额外费用，请您尽快前往归还哦~~" );

            message.setFrom("2718458328@qq.com");
            message.setTo(email);
            mailSender.send(message);
            return Result.success("邮箱提醒成功~");
        }
        return Result.error("该用户没有填写邮箱哦！");
    }

}

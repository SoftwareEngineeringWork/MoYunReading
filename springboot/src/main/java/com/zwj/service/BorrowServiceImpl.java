package com.zwj.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.BorrowPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.excepion.ServiceException;
import com.zwj.mapper.BookMapper;
import com.zwj.mapper.BorrowMapper;
import com.zwj.mapper.UserMapper;
import com.zwj.pojo.Book;
import com.zwj.pojo.Borrow;
import com.zwj.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService{

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    BookMapper bookMapper;

    @Override
    public List<Borrow> list() {
        List<Borrow> borrows = borrowMapper.list();
        //计算借出天数
        Date now = new Date();
        for (Borrow borrow : borrows) {
            borrow.setDays((int) DateUtil.betweenDay(borrow.getCreateTime(), now, false));
        }
        return borrows;
    }

    @Override
    public Borrow getBorrowById(int id) {
        return borrowMapper.getBorrowById(id);
    }

    @Override
    public Object page(BorrowPageRequest borrowPageRequest) {
        PageHelper.startPage(borrowPageRequest.getPageNum(),borrowPageRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition(borrowPageRequest);
        Date now = new Date();
        for (Borrow borrow : borrows) {
            borrow.setDays((int) DateUtil.betweenDay(borrow.getCreateTime(), now, false));
        }
        return new PageInfo<>(borrows);
    }

    @Override
    public Object page2(BorrowPageRequest borrowPageRequest) {
        PageHelper.startPage(borrowPageRequest.getPageNum(),borrowPageRequest.getPageSize());
        List<Borrow> borrows = borrowMapper.listByCondition2(borrowPageRequest);
        return new PageInfo<>(borrows);
    }

    @Override
    public int save(Borrow borrow) {
        /*
            1.校验书的数量是否足够
            2.校验用户积分是否足够
         */
        Book book = bookMapper.getBookByBookNum(borrow.getBookNum());
        if (book.getNums() < 1){
            throw new ServiceException("图书数量不足!");
        }
        User user = userMapper.findUserByUsername(borrow.getUserId());
        if (user.getAccount() < borrow.getScore()){
            throw new ServiceException("用户积分不足!");
        }
        book.setNums(book.getNums() - 1);
        bookMapper.updateNum(book);
        user.setAccount(user.getAccount() - borrow.getScore());
        userMapper.updateAccount(user);
        return borrowMapper.save(borrow);
    }

    @Override
    public int update(Borrow borrow) {
        Date now = new Date();
        borrow.setReturnTime(now);
        borrow.setStatus("已归还");
        Book book = bookMapper.getBookByBookNum(borrow.getBookNum());
        book.setNums(book.getNums() + 1);
        bookMapper.updateNum(book);
        return borrowMapper.update(borrow);
    }

    @Override
    public int deleteReturn(Borrow borrow) {
        borrow.setStatus("已借出");
        borrow.setReturnTime(null);
        return borrowMapper.deleteReturn(borrow);
    }

    @Override
    public int delete(int id) {
        Borrow borrow = borrowMapper.getBorrowById(id);
        Book book = bookMapper.getBookByBookNum(borrow.getBookNum());
        book.setNums(book.getNums() + 1);
        bookMapper.updateNum(book);
        User user = userMapper.findUserByUsername(borrow.getUserId());
        user.setAccount(user.getAccount() + borrow.getScore());
        userMapper.updateAccount(user);
        return borrowMapper.delete(id);
    }


}

package com.zwj.service;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.BookPageRequest;
import com.zwj.mapper.BookMapper;
import com.zwj.pojo.Book;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public Book findBookById(int id) {
        return bookMapper.findBookById(id);
    }

    @Override
    public Book getBookByBookNum(String bookNum) {
        return bookMapper.getBookByBookNum(bookNum);
    }

    @Override
    public Object page(BookPageRequest bookPageRequest) {
        PageHelper.startPage(bookPageRequest.getPageNum(),bookPageRequest.getPageSize());
        List<Book> Books = bookMapper.listByCondition(bookPageRequest);
        return new PageInfo<>(Books);
    }

    @Override
    public int save(Book book) {
        book.setCategory(category(book.getCategories()));
        return bookMapper.save(book);
    }

    @Override
    public int update(Book book) {
        book.setCategory(category(book.getCategories()));
        return bookMapper.update(book);
    }

    @Override
    public int updateNum(Book book) {
        return bookMapper.updateNum(book);
    }

    @Override
    public int delete(int id) {
        return bookMapper.delete(id);
    }

    private String category(List<String> categories) {
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(categories)) {
            categories.forEach(v -> sb.append(v).append(" > "));
            return sb.substring(0, sb.lastIndexOf(" > "));
        }
        return sb.toString();
    }


}

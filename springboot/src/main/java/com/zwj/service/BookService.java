package com.zwj.service;

import com.zwj.controller.request.BookPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Book;
import com.zwj.pojo.User;

import java.util.List;

public interface BookService {

    List<Book> list();

    Book findBookById(int id);

    Book getBookByBookNum(String bookNum);

    Object page(BookPageRequest bookPageRequest);

    int save(Book book);

    int update(Book book);

    int updateNum(Book book);

    int delete(int id);

}

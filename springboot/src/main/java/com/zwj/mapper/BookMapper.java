package com.zwj.mapper;

import com.zwj.controller.request.BookPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Book;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {

    Book getBookByBookNum(String bookNum);

    List<Book> list();

    Book findBookById(int id);

    List<Book> listByCondition(BookPageRequest bookPageRequest);

    int save(Book book);

    int update(Book book);

    int updateNum(Book book);

    int delete(int id);

}

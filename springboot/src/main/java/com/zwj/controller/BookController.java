package com.zwj.controller;

import com.zwj.controller.request.BookPageRequest;
import com.zwj.pojo.Book;
import com.zwj.service.BookServiceImpl;
import com.zwj.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookServiceImpl bookService;

    @PostMapping("/add")
    public Result<?> save(@RequestBody Book book){
        if (bookService.getBookByBookNum(book.getBookNum()) == null){
            int result = bookService.save(book);
            if (result == 1){
                return Result.success();
            }
        }
        return Result.error("未知错误！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int result = bookService.delete(id);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败！请检查此图书所有交易是否完成");
    }

    @GetMapping("/{id}")
    public Result<?> find(@PathVariable Integer id){
        Book book = bookService.findBookById(id);
        return Result.success(book);
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Book book){
        if (bookService.getBookByBookNum(book.getBookNum()) == null || bookService.getBookByBookNum(book.getBookNum()).getId() == book.getId()){
            book.setUpdateTime(new Date());
            int result = bookService.update(book);
            if (result == 1){
                return Result.success();
            }
        } else {
            return Result.error("序列号已存在！");
        }
        return Result.error("修改失败！");
    }


    @GetMapping("/list")
    public Result<?> list(){
        List<Book> Books = bookService.list();
        return Result.success(Books);
    }

    @GetMapping("/page")
    public Result<?> page(BookPageRequest BookPageRequest){
        return Result.success(bookService.page(BookPageRequest));
    }

}

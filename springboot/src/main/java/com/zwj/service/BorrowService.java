package com.zwj.service;

import com.zwj.controller.request.BorrowPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Borrow;
import com.zwj.pojo.User;

import java.util.List;

public interface BorrowService {

    List<Borrow> list();

    Borrow getBorrowById(int id);

    Object page(BorrowPageRequest borrowPageRequest);

    Object page2(BorrowPageRequest borrowPageRequest);

    int save(Borrow borrow);

    int update(Borrow borrow);

    int deleteReturn(Borrow borrow);

    int delete(int id);
}

package com.zwj.mapper;

import com.zwj.controller.request.BorrowPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Borrow;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowMapper {

    List<Borrow> list();

    List<Borrow> listByCondition(BorrowPageRequest borrowPageRequest);

    //还书
    List<Borrow> listByCondition2(BorrowPageRequest borrowPageRequest);

    Borrow getBorrowById(int id);

    int save(Borrow borrow);

    int update(Borrow borrow);

    int deleteReturn(Borrow borrow);

    int delete(int id);

}

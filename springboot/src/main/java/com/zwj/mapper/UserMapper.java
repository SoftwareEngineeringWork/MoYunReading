package com.zwj.mapper;

import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<User> list();

    User findUserById(int id);

    User findUserByUsername(String username);

    List<User> listByCondition(UserPageRequest userPageRequest);

    int save(User user);

    int update(User user);

    int updateAccount(User user);

    int delete(int id);

}

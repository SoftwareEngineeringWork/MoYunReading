package com.zwj.service;

import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User findUserById(int id);

    User findUserByUsername(String username);

    Object page(UserPageRequest userPageRequest);

    int save(User user);

    int update(User user);

    int updateAccount(User user);

    int delete(int id);
}

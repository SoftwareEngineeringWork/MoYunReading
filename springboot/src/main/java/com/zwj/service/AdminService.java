package com.zwj.service;

import com.zwj.controller.request.AdminPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Admin;
import com.zwj.pojo.User;

import java.util.List;

public interface AdminService {

    List<Admin> list();

    Object page(AdminPageRequest adminPageRequest);

    int save(Admin admin);

    int update(Admin admin);

    int updatePassword(Admin admin);

    int delete(int id);

    int deleteStatus(int id,int status);

    Admin getAdminById(int id);

    Admin getAdminByUsernameAndPassword(String username,String password);
}

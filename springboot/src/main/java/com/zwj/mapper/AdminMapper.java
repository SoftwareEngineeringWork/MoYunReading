package com.zwj.mapper;

import com.zwj.controller.request.AdminPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Admin;
import com.zwj.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminMapper {

    List<Admin> list();

    List<Admin> listByCondition(AdminPageRequest adminPageRequest);

    int save(Admin admin);

    int update(Admin admin);

    Admin getAdminById(int id);

    int delete(int id);

    int deleteStatus(int id,int status);

    Admin getAdminByUsernameAndPassword(String username,String password);

    int updatePassword(Admin admin);

}

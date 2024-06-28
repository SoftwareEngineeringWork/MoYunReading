package com.zwj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwj.controller.request.AdminPageRequest;
import com.zwj.mapper.AdminMapper;
import com.zwj.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public Object page(AdminPageRequest adminPageRequest) {
        PageHelper.startPage(adminPageRequest.getPageNum(),adminPageRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(adminPageRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public int save(Admin admin) {
        return adminMapper.save(admin);
    }

    @Override
    public int update(Admin admin) {
        return adminMapper.update(admin);
    }

    @Override
    public int updatePassword(Admin admin) {
        return adminMapper.updatePassword(admin);
    }

    @Override
    public int delete(int id) {
        return adminMapper.delete(id);
    }

    @Override
    public int deleteStatus(int id,int status) {
        return adminMapper.deleteStatus(id,status);
    }

    @Override
    public Admin getAdminById(int id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public Admin getAdminByUsernameAndPassword(String username, String password) {
        return adminMapper.getAdminByUsernameAndPassword(username,password);
    }
}

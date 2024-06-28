package com.zwj.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.zwj.controller.request.AdminPageRequest;
import com.zwj.controller.request.UserPageRequest;
import com.zwj.pojo.Admin;
import com.zwj.pojo.User;
import com.zwj.service.AdminService;
import com.zwj.service.AdminServiceImpl;
import com.zwj.service.UserServiceImpl;
import com.zwj.utils.MD5Util;
import com.zwj.utils.Result;
import com.zwj.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Security;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    AdminServiceImpl adminService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Admin user){
        //我们要把admin的信息返给前端，如果不修改，密码就会被盗取！
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"UTF-8"));
        Admin admin = adminService.getAdminByUsernameAndPassword(user.getUsername(),user.getPassword());
        if (admin != null){
            if (admin.getStatus() == 1){
                String token = TokenUtil.genToken(String.valueOf(admin.getId()), admin.getPassword());
                admin.setPassword("");
                admin.setCreatetime(null);
                admin.setUpdatetime(null);
                admin.setToken(token);
                return Result.success(admin);
            } else {
                return Result.error("您的权限已冻结!");
            }
        }
        return Result.error("用户名或密码错误!");
    }

    @PostMapping("/add")
    public Result<?> save(@RequestBody Admin admin){
        admin.setPassword(MD5Util.MD5Encode("123456789","UTF-8"));
        int result = adminService.save(admin);
        if (result == 1){
            return Result.success();
        }
        return Result.error("未知错误！");
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable Integer id){
        int result = adminService.delete(id);
        if (result == 1){
            return Result.success();
        }
        return Result.error("删除失败！");
    }

    @PutMapping("/delete/{id}")
    public Result<?> updateStatus(@PathVariable Integer id){
        Admin admin = adminService.getAdminById(id);
        admin.setStatus((admin.getStatus()+1) % 2);
        int result = adminService.deleteStatus(id,admin.getStatus());
        if (result == 1){
            return Result.success();
        }
        return Result.error("禁用失败!");
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Admin admin){
        admin.setUpdatetime(new Date());
        int result = adminService.update(admin);
        if (result == 1){
            return Result.success();
        }
        return Result.error("更新失败！");
    }

    @PutMapping("/update")
    public Result<?> updatePassword(@RequestBody Admin admin){
        admin.setUpdatetime(new Date());
        admin.setPassword(MD5Util.MD5Encode(admin.getPassword(),"UTF-8"));
        int result = adminService.updatePassword(admin);
        if (result == 1){
            return Result.success();
        }
        return Result.error("修改密码失败！");
    }


    @GetMapping("/list")
    public Result<?> list(){
        List<Admin> admins = adminService.list();
        return Result.success(admins);
    }

    @GetMapping("/page")
    public Result<?> page(AdminPageRequest adminPageRequest){
        return Result.success(adminService.page(adminPageRequest));
    }

}

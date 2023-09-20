package com.zzb.shopping.controller;

import com.zzb.shopping.common.util.JwtUtils;
import com.zzb.shopping.common.util.ResultModel;
import com.zzb.shopping.common.validation.admin.AdminLogin;
import com.zzb.shopping.dto.AdminLoginDTO;
import com.zzb.shopping.model.Admin;
import com.zzb.shopping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("login")
    public ResultModel login(@Validated(AdminLogin.class) AdminLoginDTO adminLoginDto){
       Admin admin =  adminService.login(adminLoginDto);
        if(admin != null) {
            String token = JwtUtils.createToken(admin.getId(), admin.getName());
            Map resultMap = new HashMap();
            resultMap.put("id", admin.getId());
            resultMap.put("token", token);
            return ResultModel.success(resultMap);
        }else {
            return ResultModel.success("用户名密码错误");
        }
    }

    @RequestMapping("add")
    public ResultModel add(@Validated(AdminLogin.class) AdminLoginDTO adminLoginDTO){
        try {
            adminService.add(adminLoginDTO);
            return ResultModel.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.error("添加失败");
        }
    }
}

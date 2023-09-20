package com.zzb.shopping.dto;

import com.zzb.shopping.common.validation.admin.AdminLogin;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AdminLoginDTO {
    @NotBlank(message = "用户名不能为空!",groups = {AdminLogin.class})
//    @Pattern(regexp = "^[A-Za-z0-9]{6,20}$", message = "用户名格式错误", groups = {AdminLogin.class})
    String name;
    @NotBlank(message = "密码不能为空!",groups = {AdminLogin.class})
    String password;
}

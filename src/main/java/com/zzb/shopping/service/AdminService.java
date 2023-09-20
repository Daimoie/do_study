package com.zzb.shopping.service;


import com.zzb.shopping.dto.AdminLoginDTO;
import com.zzb.shopping.model.Admin;

public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDto);

    void add(AdminLoginDTO adminLoginDTO);

}

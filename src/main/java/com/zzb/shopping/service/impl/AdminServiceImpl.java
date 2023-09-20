package com.zzb.shopping.service.impl;

import com.zzb.shopping.dao.AdminDAO;
import com.zzb.shopping.dao.AdminSpecDAO;
import com.zzb.shopping.dto.AdminLoginDTO;
import com.zzb.shopping.model.Admin;
import com.zzb.shopping.model.AdminExample;
import com.zzb.shopping.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDAO adminDao;
    @Autowired
    AdminSpecDAO adminSpecDAO;

    @Override
    public Admin login(AdminLoginDTO adminLoginDto) {
        AdminExample ex = new AdminExample();
        ex.createCriteria().andNameEqualTo(adminLoginDto.getName()).andPasswordEqualTo(adminLoginDto.getPassword());
        List<Admin> adminList = adminDao.selectByExample(ex);
        return adminList!=null&&adminList.size()>0?adminList.get(0):null;
    }

    @Override
    public void add(AdminLoginDTO adminLoginDTO) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

       List<Admin> adminList =  adminSpecDAO.findById(arrayList);
        for (Admin admin : adminList) {
            System.out.println("admin = " + admin);
        }
}


}

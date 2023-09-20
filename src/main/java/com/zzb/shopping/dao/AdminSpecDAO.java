package com.zzb.shopping.dao;

import com.zzb.shopping.dto.AdminLoginDTO;
import com.zzb.shopping.model.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AdminSpecDAO {
    void add(List list);

    List<Admin> findById(@Param("list") ArrayList arrayList);
}

package com.zzb.shopping.dao;

import com.zzb.shopping.model.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSpecDAO {
    List<Product> list(@Param("keyword") String keyword,@Param("startNum") Integer startNum,@Param("pageSize") Integer pageSize);

    int add(Product product);
}

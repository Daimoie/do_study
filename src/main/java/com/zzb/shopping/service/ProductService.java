package com.zzb.shopping.service;

import com.zzb.shopping.dto.ProductFenyeDTO;
import com.zzb.shopping.model.Product;
import com.zzb.shopping.common.util.ResultModel;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    Boolean add(Product product, HttpServletRequest request);

    Boolean update(Product product, HttpServletRequest request);

    Boolean del(Product product, HttpServletRequest request);

    ResultModel<ProductFenyeDTO> SearchList(ProductFenyeDTO productFenyeDto);

    byte[] getXslx();

}

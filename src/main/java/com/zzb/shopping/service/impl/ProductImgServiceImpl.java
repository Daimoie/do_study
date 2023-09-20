package com.zzb.shopping.service.impl;

import com.zzb.shopping.dao.ProductImgDAO;
import com.zzb.shopping.model.ProductImg;
import com.zzb.shopping.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductImgServiceImpl implements ProductImgService {
    @Autowired
    ProductImgDAO productImgDAO;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean add(Integer pid, Integer img_id) {
        ProductImg productImg = new ProductImg();
        productImg.setProductId(pid);
        productImg.setImgId(img_id);
        productImg.setIsDelect(0);
//        throw new RuntimeException();
        return productImgDAO.insert(productImg)>0;
    }
}

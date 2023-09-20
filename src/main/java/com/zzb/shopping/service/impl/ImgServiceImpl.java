package com.zzb.shopping.service.impl;

import com.zzb.shopping.dao.ImgDAO;
import com.zzb.shopping.dao.ImgSpecDAO;
import com.zzb.shopping.model.Img;
import com.zzb.shopping.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ImgServiceImpl implements ImgService {
    @Autowired
    ImgDAO imgDAO;
    @Autowired
    ImgSpecDAO imgSpecDAO;

    @Override
    public Boolean add(Img img) {
        return imgSpecDAO.add(img)>0;
    }
}

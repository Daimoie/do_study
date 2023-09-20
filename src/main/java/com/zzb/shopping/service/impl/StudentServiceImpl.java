package com.zzb.shopping.service.impl;

import com.zzb.shopping.common.util.Creatcode;
import com.zzb.shopping.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public void sendEmail(String receiverEmail) {
        String code = Creatcode.creatCode(5);
        System.out.println("code = " + code);
    }
}

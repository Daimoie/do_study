package com.zzb.shopping.common.dozer;

import com.zzb.shopping.common.util.Base64ToImg;
import com.zzb.shopping.common.util.ResultModel;
import com.zzb.shopping.dto.ImgDTO;
import com.zzb.shopping.model.Img;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
@Component
public class ReturnImg {
    //本地文件地址
    @Value("${img.save-path}")
    private String filePath;
    //url
    @Value("${img.domain}")
    private String domain;
    private  MultipartFile fileUpload;
    private  String base64Msg;
    public ReturnImg() {

    }

    public Img getImg(ImgDTO imgDTO) throws IOException {
        //获取Mult文件
        fileUpload = imgDTO.getFileUpload();
        //获取Base64
        base64Msg = imgDTO.getBase64Msg();
        System.out.println("filePath = " + filePath);
        //file上传处理
        if(fileUpload!=null){
            //获取文件名
            String fileName = fileUpload.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //重新生成文件名
            fileName = UUID.randomUUID()+suffixName;
            Img img = new Img();
            img.setImgName(fileName);
            img.setImgLocation(filePath+fileName);
            img.setDomain(domain);
            img.setIsDelect(0);
            fileUpload.transferTo(new File(filePath+fileName));
            return img;
        }
        else {  //base64上传处理
            String fileName = UUID.randomUUID()+".jpg";
            File file = Base64ToImg.convertBase64ToFile(base64Msg, filePath, fileName);

            Img img = new Img();
                img.setImgName(fileName);
                img.setImgLocation(filePath+fileName);
                img.setDomain(domain);
                img.setIsDelect(0);
                return img;
        }
    }
}

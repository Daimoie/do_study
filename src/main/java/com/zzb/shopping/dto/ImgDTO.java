package com.zzb.shopping.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImgDTO {
    private MultipartFile fileUpload;
    private String base64Msg;
}

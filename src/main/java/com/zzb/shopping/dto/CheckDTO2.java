package com.zzb.shopping.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CheckDTO2 {
    @NotBlank(message = "id 不能为空" )
    private String id;
    @Valid
    private AdminLoginDTO adminLoginDTO;
}

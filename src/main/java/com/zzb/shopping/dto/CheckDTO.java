package com.zzb.shopping.dto;

import com.zzb.shopping.common.validation.student.Check1;
import com.zzb.shopping.common.validation.student.Check2;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class CheckDTO {
    @NotNull(message = "id不能为空" , groups = {Check1.class})
    @Max(message = "id不能大于5" ,value = 5, groups = {Check2.class})
    @Min(message = "id不能小于1" ,value = 1,groups = {Check1.class})
    Integer id;
    @NotBlank(message = "name不能为空" , groups = {Check1.class})
    String name;
    @NotBlank(message = "sex不能为空" , groups = {Check1.class})
    String sex;
    @NotBlank(message = "idCard不能为空" , groups = {Check1.class})
    String idCard;
    @NotNull(message = "num不能为空" , groups = {Check1.class})
    Integer num;
    @Email(message = "email格式不对", groups = {Check2.class})
    String email;
    @Valid
    AdminLoginDTO adminLoginDTO;
}

package com.zzb.shopping.controller;

import com.zzb.shopping.common.util.ResultModel;
import com.zzb.shopping.common.validation.student.Check1;
import com.zzb.shopping.common.validation.student.Check2;
import com.zzb.shopping.dto.CheckDTO;
import com.zzb.shopping.dto.CheckDTO2;
import com.zzb.shopping.model.Admin;
import com.zzb.shopping.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("study")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("check1")
    public ResultModel check1(@RequestBody  @Validated(Check1.class) CheckDTO checkDTO){
        return ResultModel.success("OK");
    }

    @RequestMapping("check2")
    public ResultModel check2(@RequestBody  @Validated(Check2.class) CheckDTO checkDTO){
        return ResultModel.success("OK");
    }
    @RequestMapping("check3")
    public ResultModel check3( @NotBlank(message = "name不能为空") String name){
        return ResultModel.success("OK");
    }
    @RequestMapping("check4")
    public ResultModel check4(@RequestBody  @Validated CheckDTO2 checkDTO){
        return ResultModel.success("OK"+checkDTO);
    }
    @RequestMapping("sendEmail")
    public ResultModel sendEmail( @NotBlank(message = "发送邮箱不能为空") String receiverEmail){
        try {
            studentService.sendEmail(receiverEmail);
            return ResultModel.success("消息已发送，请耐心等待");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResultModel.error("发送失败,请检查您的邮箱");
        }
    }
    @RequestMapping("stream")
    public ResultModel streamTest(){
        List<Admin> adminList = new ArrayList<>();
        adminList.add(new Admin("admin","password1",1));
        adminList.add(new Admin("admin2","password2",1));
        adminList.add(new Admin("admin3","password3",1));
        adminList.add(new Admin("admin4","password4",1));
        adminList.add(new Admin("admin5","password5",1));
        adminList.add(new Admin("admin6","password6",1));
        //没用，测试能不能嵌套
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("admi4");
        List<Admin> ad = adminList.stream()
                .filter(admin ->admin.getName().equals("admin3") ).collect(Collectors.toList());
        List<Admin> collect = adminList.stream()
                .map(admin -> admin.changeName("123")).collect(Collectors.toList());
        return   ResultModel.success(collect);
    }

}

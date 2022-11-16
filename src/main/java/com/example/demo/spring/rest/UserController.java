package com.example.demo.spring.rest;

import com.example.demo.common.mapstruct.bean.dto.UserDTO;
import com.example.demo.common.mapstruct.bean.po.User;
import com.example.demo.common.mapstruct.converter.UserConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author yanzx
 * @date 2022/8/24 21:59
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserConverter userConverter;

    @GetMapping("/convertDTO")
    public UserDTO converterToUserDTO(){
        User user = User.builder().id(123).name("yanzx").createTime("20220824").updateTime(LocalDateTime.now()).build();
        return userConverter.convertToUserDTO(user);
    }

}

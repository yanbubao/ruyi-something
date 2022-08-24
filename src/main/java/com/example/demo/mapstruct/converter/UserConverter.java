package com.example.demo.mapstruct.converter;

import com.example.demo.mapstruct.bean.dto.UserDTO;
import com.example.demo.mapstruct.bean.po.User;
import com.example.demo.mapstruct.bean.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * @author yanzx
 * @date 2022/8/24 21:50
 */
@Mapper(componentModel = "spring")
public interface UserConverter {
    UserDTO convertToUserDTO(User user);
    UserVO convertToUserVO(UserDTO userDTO);
}

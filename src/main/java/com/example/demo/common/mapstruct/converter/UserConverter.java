package com.example.demo.common.mapstruct.converter;

import com.example.demo.common.mapstruct.bean.dto.UserDTO;
import com.example.demo.common.mapstruct.bean.po.User;
import com.example.demo.common.mapstruct.bean.vo.UserVO;
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

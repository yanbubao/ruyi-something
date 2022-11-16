package com.example.demo.common.mapstruct.bean.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yanzx
 * @date 2022/8/24 21:41
 */
@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String createTime;
    private LocalDateTime updateTime;
}

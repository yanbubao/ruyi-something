package com.example.demo.dubbo.context;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author yanzx
 * @Date 2022/11/17 22:43
 */
@Data
@ToString
public class CommonRequest implements Serializable {
    private static final long serialVersionUID = -6259783127960694778L;

    private String traceId;
    private long userId;
}

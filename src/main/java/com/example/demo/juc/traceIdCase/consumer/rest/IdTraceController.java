package com.example.demo.juc.traceIdCase.consumer.rest;

import com.example.demo.dubbo.interfaces.UserRpcService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yanzx
 * @Date 2022/11/17 23:19
 */
@RestController
@RequestMapping(value = "/id-trace")
public class IdTraceController {

    @DubboReference
    private UserRpcService userRpcService;

    @GetMapping(value = "/do-trace")
    public String doIdTrace(){
        boolean userExist = userRpcService.isUserExist(1001);
        return "success";
    }
}

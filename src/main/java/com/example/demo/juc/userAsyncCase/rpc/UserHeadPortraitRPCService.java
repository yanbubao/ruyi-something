package com.example.demo.juc.userAsyncCase.rpc;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @Author idea
 * @Date created in 6:47 下午 2022/7/4
 */
@Slf4j
public class UserHeadPortraitRPCService {

    public List<String> queryUserHeadPortrait(long userId) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            log.error("error msg:", e);
        }
        return Arrays.asList("pic-1.jpg", "pic-2.jpg", "pic-3.jpg");
    }

}

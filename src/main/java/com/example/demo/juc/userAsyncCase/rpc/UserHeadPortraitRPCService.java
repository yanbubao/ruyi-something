package com.example.demo.juc.userAsyncCase.rpc;

import java.util.Arrays;
import java.util.List;

/**
 * @Author idea
 * @Date created in 6:47 下午 2022/7/4
 */
public class UserHeadPortraitRPCService {

    public List<String> queryUserHeadPortrait(long userId){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList("pic-1.jpg","pic-2.jpg","pic-3.jpg");
    }

}

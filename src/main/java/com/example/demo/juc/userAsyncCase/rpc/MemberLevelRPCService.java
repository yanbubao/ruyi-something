package com.example.demo.juc.userAsyncCase.rpc;

/**
 * @Author idea
 * @Date created in 6:46 下午 2022/7/4
 */
public class MemberLevelRPCService {

    public int queryUserLevel(long userId){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}

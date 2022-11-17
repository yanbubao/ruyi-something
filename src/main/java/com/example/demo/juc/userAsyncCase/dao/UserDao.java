package com.example.demo.juc.userAsyncCase.dao;

import com.example.demo.juc.userAsyncCase.bean.UserInfoPO;

/**
 * @Author idea
 * @Date created in 5:20 下午 2022/7/4
 */
public class UserDao {

    public UserInfoPO queryUserInfo(long userId){
        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setAge((short) 11);
        userInfoPO.setTel("1677839102");
        userInfoPO.setSex((short) 1);
        userInfoPO.setUserName("idea");
        userInfoPO.setUserId(userId);
        return userInfoPO;
    }

}

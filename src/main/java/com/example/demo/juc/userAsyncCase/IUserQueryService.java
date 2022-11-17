package com.example.demo.juc.userAsyncCase;

import com.example.demo.juc.userAsyncCase.bean.UserInfoDTO;

/**
 * @Author idea
 * @Date created in 6:54 下午 2022/7/4
 */
public interface IUserQueryService {

    /**
     * 按照id查询
     *
     * @param userId
     * @return
     */
    UserInfoDTO queryUserInfoWrapper(long userId);
}

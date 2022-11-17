package com.example.demo.juc.userAsyncCase.service.sync;

import com.example.demo.juc.userAsyncCase.IUserQueryService;
import com.example.demo.juc.userAsyncCase.bean.UserInfoDTO;
import com.example.demo.juc.userAsyncCase.bean.UserInfoPO;
import com.example.demo.juc.userAsyncCase.dao.UserDao;
import com.example.demo.juc.userAsyncCase.rpc.MemberLevelRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserHeadPortraitRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserVerifyRPCService;
import org.springframework.beans.BeanUtils;

/**
 * @Author yanzx
 * @Date 2022/11/17 17:20
 */
public class UserQueryService implements IUserQueryService {

    private final UserDao userDao = new UserDao();
    private final UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private final MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private final UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();

    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);
        userInfoDTO.setMemberLevel(memberLevelRPCService.queryUserLevel(userId));
        userInfoDTO.setHeadPortrait(userHeadPortraitRPCService.queryUserHeadPortrait(userId));
        userInfoDTO.setVerifyStatus(userVerifyRPCService.queryUserVerifyStatus(userId));
        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserQueryService userQueryService = new UserQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(1001);
        long end = System.currentTimeMillis();
        System.out.println("请求耗时：" + (end - begin) + "ms, " + userInfoDTO);
    }
}

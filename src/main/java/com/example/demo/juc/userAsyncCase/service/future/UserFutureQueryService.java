package com.example.demo.juc.userAsyncCase.service.future;

import com.example.demo.juc.userAsyncCase.IUserQueryService;
import com.example.demo.juc.userAsyncCase.bean.UserInfoDTO;
import com.example.demo.juc.userAsyncCase.bean.UserInfoPO;
import com.example.demo.juc.userAsyncCase.dao.UserDao;
import com.example.demo.juc.userAsyncCase.rpc.MemberLevelRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserHeadPortraitRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserVerifyRPCService;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author yanzx
 * @Date 2022/11/17 17:26
 */
public class UserFutureQueryService implements IUserQueryService {

    private final UserDao userDao = new UserDao();

    private final UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private final MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private final UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();

    /**
     * ThreadPoolExecutor供开发者自定义异步线程池。如果说我们没有去显式定义线程池的话，
     * 那么底层默认是会使用 ForkJoinPool 线程池，而这类线程的本质其实是守护线程，也就意味着当主线程退出之后，
     * 守护线程还可能会在执行，只有当JVM 关闭的时候，守护线程才会停止。
     */
    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(4, 8, 3000,
                    TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000));


    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);

        Future<Integer> memberLevelFuture = executor.submit(() -> memberLevelRPCService.queryUserLevel(userId));
        Future<List<String>> userHeadPortraitFuture = executor.submit(() -> userHeadPortraitRPCService.queryUserHeadPortrait(userId));
        Future<Boolean> userVerifyStatusFuture = executor.submit(() -> userVerifyRPCService.queryUserVerifyStatus(userId));

        try {
            // notice: 这三步get会顺序阻塞，可使用CompletableFuture解决该问题
            userInfoDTO.setMemberLevel(memberLevelFuture.get(1, TimeUnit.SECONDS));
            userInfoDTO.setHeadPortrait(userHeadPortraitFuture.get(1, TimeUnit.SECONDS));
            userInfoDTO.setVerifyStatus(userVerifyStatusFuture.get(1, TimeUnit.SECONDS));

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserFutureQueryService userFutureQueryService = new UserFutureQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userFutureQueryService.queryUserInfoWrapper(1001);
        long end = System.currentTimeMillis();
        System.out.println("请求耗时：" + (end - begin) + "ms, " + userInfoDTO);
    }
}

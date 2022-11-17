package com.example.demo.juc.userAsyncCase.service.completableFuture;

import com.example.demo.juc.userAsyncCase.IUserQueryService;
import com.example.demo.juc.userAsyncCase.bean.UserInfoDTO;
import com.example.demo.juc.userAsyncCase.bean.UserInfoPO;
import com.example.demo.juc.userAsyncCase.dao.UserDao;
import com.example.demo.juc.userAsyncCase.rpc.MemberLevelRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserHeadPortraitRPCService;
import com.example.demo.juc.userAsyncCase.rpc.UserVerifyRPCService;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author yanzx
 * @Date 2022/11/17 17:40
 */
public class UserCompletableFutureQueryService implements IUserQueryService {

    private final UserDao userDao = new UserDao();

    private final UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private final MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private final UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();


    /**
     * 1.初始化CompletableFuture的方式
     * supplyAsync 方法可以在执行完异步任务之后，获取到任务的执行结果。
     * runAsync 方法在执行完异步任务之后，不会返回对应的结果。
     * <p>
     * <p>
     * 2.CompletableFuture获取结果的方式
     * public T    get()
     * public T    get(long timeout, TimeUnit unit)
     * public T    getNow(T valueIfAbsent)
     * public T    join()
     * 说明：
     * 关于获取结果方面，CompletableFuture 和 Future 都有些类似，在使用 get 方式获取结果时，
     * 如果结果没有生成，那么都会阻塞主线程。 join 和 get 两种方式在使用的时候基本差不多，只不过 join获取结果的时候，
     * 如果异步任务有异常抛出的话，会获取到一个 CompletionException，而 get 获取的是一个具体的异常。
     * <p>
     * <p>
     * 3.CompletableFuture完成结果后的操作
     * public CompletableFuture<T>     whenComplete(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T>     whenCompleteAsync(BiConsumer<? super T,? super Throwable> action)
     * public CompletableFuture<T>     whenCompleteAsync(BiConsumer<? super T,? super Throwable> action, Executor executor)
     * public CompletableFuture<T>     exceptionally(Function<Throwable,? extends T> fn)
     * 说明：
     * 当 CompletableFuture 执行完结果之后，会有两种情况，一种是直接返回结果，一种是抛出异常，上边的方法 1、2、3 都比较类似，
     * 都是在异步任务执行完毕之后会回调用的位置，差别在于回调的处理流程是否异步化，而且异步处理的线程是否可以支持自定义。
     * 而 exceptionally 则是当有异常发生的时候会触发的函数。
     * <p>
     * <p>
     * 4.CompletableFuture获取率先完成的任务和所有完成的任务
     * public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs)
     * public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs)
     * 说明：
     * 在使用 CompletableFutrue 的时候，它的内部有两个方法，分别是 anyof 和 allof，这两个方法，
     * anyOf 是用于获取一批的 CompletableFuture 中率先完成任务的结果，
     * allOf 则用于获取一批的 CompletableFuture 全部完成任务的结果。
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);

        CompletableFuture<Boolean> userVerifyFuture =
                CompletableFuture.supplyAsync(() -> userVerifyRPCService.queryUserVerifyStatus(userId))
                        .whenComplete((verifyStatus, throwable) -> userInfoDTO.setVerifyStatus(verifyStatus));

        CompletableFuture<Integer> memberLevelFuture =
                CompletableFuture.supplyAsync(() -> memberLevelRPCService.queryUserLevel(userId))
                        .whenComplete((integer, throwable) -> userInfoDTO.setMemberLevel(integer));

        CompletableFuture<List<String>> userHeadPortraitFuture =
                CompletableFuture.supplyAsync(() -> userHeadPortraitRPCService.queryUserHeadPortrait(userId))
                        .whenComplete((resultList, throwable) -> userInfoDTO.setHeadPortrait(resultList));

        // 汇总CompletableFuture
        CompletableFuture<Void> completableFuture =
                CompletableFuture.allOf(userVerifyFuture, memberLevelFuture, userHeadPortraitFuture);

        try {
            completableFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserCompletableFutureQueryService userCompletableFutureQueryService = new UserCompletableFutureQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userCompletableFutureQueryService.queryUserInfoWrapper(1001);
        long end = System.currentTimeMillis();
        System.out.println("请求耗时：" + (end - begin) + "ms, " + userInfoDTO);
    }
}

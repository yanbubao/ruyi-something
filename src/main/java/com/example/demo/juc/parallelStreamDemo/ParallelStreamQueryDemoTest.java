package com.example.demo.juc.parallelStreamDemo;

import com.example.demo.juc.userAsyncCase.bean.UserInfoDTO;
import com.example.demo.juc.userAsyncCase.service.sync.UserQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @Author yanzx
 * @Date 2022/11/17 19:12
 */
public class ParallelStreamQueryDemoTest {

    private final UserQueryService userQueryService = new UserQueryService();
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() * 2);


    public static void main(String[] args) {
        List<Long> userIdList = Arrays.asList(1001L, 1002L, 1003L, 1004L, 1005L, 1001L, 1002L, 1003L, 1004L, 1005L, 1001L, 1002L, 1003L, 1004L, 1005L, 1001L, 1002L, 1003L, 1004L, 1005L, 1001L, 1002L, 1003L, 1004L, 1005L);
        ParallelStreamQueryDemoTest demo = new ParallelStreamQueryDemoTest();

        long begin0 = System.currentTimeMillis();
        demo.batchQuery(userIdList);
        long end0 = System.currentTimeMillis();
        System.out.println("耗时：" + (end0 - begin0) + "ms");

        long begin = System.currentTimeMillis();
        demo.batchQueryWithParallelV1(userIdList);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin) + "ms");

        long begin2 = System.currentTimeMillis();
        demo.batchQueryWithParallelV2(userIdList);
        long end2 = System.currentTimeMillis();
        System.out.println("耗时：" + (end2 - begin2) + "ms");
    }

    /**
     * 使用parallelStream的底层自带的ForkJoinPool线程池
     *
     * @param userIdList
     * @return
     */
    public List<UserInfoDTO> batchQueryWithParallelV1(List<Long> userIdList) {
        List<UserInfoDTO> resultList = new ArrayList<>();
        userIdList.parallelStream().forEach(userId -> {
            UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
            resultList.add(userInfoDTO);
        });
        return resultList;
    }

    /**
     * 不使用parallelStream
     *
     * @param userIdList
     * @return
     */
    public List<UserInfoDTO> batchQuery(List<Long> userIdList) {
        List<UserInfoDTO> resultList = new ArrayList<>();
        userIdList.forEach(userId -> {
            UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
            resultList.add(userInfoDTO);
        });
        return resultList;
    }

    /**
     * 采用了自定义的forkJoinPool
     * 修改parallelStream底层 ForkJoinPool，具体的修改逻辑是，
     * 通过自定义一个 ForkJoinPool 接着往这个pool里面丢任务即可
     *
     * @param userIdList
     * @return
     */
    public List<UserInfoDTO> batchQueryWithParallelV2(List<Long> userIdList) {
        Future<List<UserInfoDTO>> resultFuture = forkJoinPool.submit(() -> {
            List<UserInfoDTO> resultList = new ArrayList<>();
            userIdList.parallelStream().forEach(userId -> {
                UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
                resultList.add(userInfoDTO);
            });
            return resultList;
        });
        try {
            return resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

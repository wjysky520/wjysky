package com.wjysky.task.crads;

import com.wjysky.entity.bo.poker.PokerBO;
import com.wjysky.entity.bo.poker.PokerPlayerInfoBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @ClassName : PokerTask
 * @Description : 扑克类游戏的任务类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-07 21:09:25
 */
@Component
@Slf4j
public class PokerTask {

    /**
     *
     * @ClassName PokerTask
     * @Title dealPoker
     * @Description 发牌
     * @param pokerBox
     * @param playerList
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/09/07 21:32:01
     * @Return
     * @throws
    **/
    @Async("cardsGameTaskExecutor")
    public Future<String> dealPoker(List<PokerBO> pokerBox, List<PokerPlayerInfoBO> playerList) {
        long startTime = System.currentTimeMillis();
        try {

        } catch (Exception e) {
            log.error("发牌时异常", e);
        }
        log.info("发牌结束，耗时[{}]毫秒", (System.currentTimeMillis() - startTime));
        return new AsyncResult<>("发牌结束");
    }

}
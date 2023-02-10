package com.wjysky.feign.controller.cards.poker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : FightLandlordController
 * @Description : 斗地主游戏
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-07 21:59:37
 */
@RestController
@RequestMapping(value = "/fightLandlord")
@Slf4j
public class FightLandlordController {

    @RequestMapping(value = "/info")
    public String info(@RequestParam(value = "msg") String msg) {
        log.info("收到了消息：" + msg);
        return "welcome to demo-springboot: " + msg;
    }
}
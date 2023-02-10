package com.wjysky.factory.cards;

import com.wjysky.pojo.DataApi;
import com.wjysky.pojo.bo.poker.PokerBO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : Cards
 * @Description : 牌类游戏的基本类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 22:26:31
 */
public interface Cards extends Game {

    /**
     *
     * @ClassName Cards
     * @Title init
     * @Description 初始化牌类信息
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:33:27
     * @Return 
     * @throws 
    **/
    public DataApi init();

    /**
     *
     * @ClassName Cards
     * @Title shuffle
     * @Description 洗牌
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:33:51
     * @Return 
     * @throws 
    **/
    public DataApi shuffle();

    /**
     *
     * @ClassName Cards
     * @Title cut
     * @Description 切牌
     * @param
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:34:24
     * @Return
     * @throws
    **/
    public DataApi cut();

    /**
     *
     * @ClassName Cards
     * @Title deal
     * @Description 发牌
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:34:48
     * @Return 
     * @throws 
    **/
    public DataApi<Map<String, List<PokerBO>>> deal();

    /**
     *
     * @ClassName Cards
     * @Title sort
     * @Description 理牌
     * @param
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:35:28
     * @Return
     * @throws
    **/
    public DataApi<Map<String, List<PokerBO>>> sort(String userId);
    
    /**
     *
     * @ClassName Cards
     * @Title draw
     * @Description 摸牌
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:36:36
     * @Return 
     * @throws 
    **/
    public DataApi draw();

    /**
     *
     * @ClassName Cards
     * @Title play
     * @Description 出牌
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:37:24
     * @Return 
     * @throws 
    **/
    public DataApi play();
    
    /**
     *
     * @ClassName Cards
     * @Title disCard
     * @Description 弃牌
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:37:56
     * @Return 
     * @throws 
    **/
    public DataApi disCard();

    /**
     *
     * @ClassName Cards
     * @Title rule
     * @Description 比较出牌大小规则
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/09/06 23:44:02
     * @Return 
     * @throws 
    **/
    public DataApi rule();

    /**
     *
     * @ClassName Cards
     * @Title win
     * @Description 获胜判定
     * @param 
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:38:20
     * @Return 
     * @throws 
    **/
    public DataApi win();
}
package com.wjysky.factory.cards.proc;

import com.alibaba.fastjson.JSON;
import com.wjysky.factory.cards.Cards;
import com.wjysky.pojo.DataApi;
import com.wjysky.pojo.bo.PlayerInfoBO;
import com.wjysky.pojo.bo.poker.PokerBO;
import com.wjysky.pojo.bo.poker.PokerPlayerInfoBO;
import com.wjysky.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName : PokerGame
 * @Description : 扑克类基本功能
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 22:49:16
 */
@Slf4j
public abstract class Poker implements Cards {

    public static final String[] suitArray = {"spade", "heart", "diamond", "club"};
//    public final String[] suitArray = {"♤", "♡", "♧", "♢"};

    public final String[] pipArray1 = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public final String[] pipArray2 = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};

    public String gameName;

    public String[] pipArray; // 牌点数组

    public List<PokerBO> pokerPool = new LinkedList<>(); // 扑克牌池

    public List<PokerBO> pokerBox = new LinkedList<>(); // 发牌盒，存放已洗好的牌

    public PokerBO[] holeCardBox; // 底牌盒，存放底牌

    public int playerCount; // 玩家人数

    public int pokerCount; // 扑克的副数

    public boolean hasJoker; // 是否有大小鬼牌

    public List<PokerPlayerInfoBO> playerList;

    public int round; // 回合

    public int order; // 当前出牌顺位

    public List<PokerBO> playPokerList = new ArrayList<>(); // 当前出牌

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title init
     * @Description 初始化牌类信息
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:33:27
     * @Return
     **/
    public DataApi init() {
        log.info("开始初始化{}......", gameName);

        playerList.sort(Comparator.comparing(PlayerInfoBO::getOrder));

        try {
            for (int i = 0; i < pokerCount; i++) {
                for (int j = 0; j < suitArray.length; j++) {
                    for (int k = 0; k < pipArray.length; k++) {
                        PokerBO pokerBO = new PokerBO(i, suitArray[j], pipArray[k], getIndex(pipArray[k]), (i + 1) * (j + 1) * (k + 1));
                        pokerPool.add(pokerBO);
                    }
                }
                if (hasJoker) {
                    PokerBO smallJokerPokerBO = new PokerBO(i, "small", "joker", 14, (i + 1) * 53);
                    pokerPool.add(smallJokerPokerBO);
                    PokerBO bigJokerPokerBO = new PokerBO(i, "big", "joker", 15, (i + 1) * 54);
                    pokerPool.add(bigJokerPokerBO);
                }
            }
        } catch (Exception e) {
            log.error("{}初始化异常", gameName, e);
            return DataApi.generateExceptionMsg(String.format("%s初始化异常", gameName));
        }
        log.info("{}初始化成功", gameName);
        return DataApi.generateSuccessMsg(String.format("%s初始化成功", gameName));
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title shuffle
     * @Description 洗牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:33:51
     * @Return
     **/
    public DataApi shuffle() {
        log.info("开始{}洗牌......", gameName);
        for (int i = pokerPool.size(); i > 0; i--) {
            int index = new Random().nextInt(i);
            pokerBox.add(pokerPool.get(index));
            pokerPool.remove(index);
        }
        log.info("{}洗牌完毕{}", gameName, JSON.toJSONString(pokerBox));
        return DataApi.generateSuccessMsg(String.format("%s洗牌完毕", gameName));
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title cut
     * @Description 切牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:34:24
     * @Return
     **/
    public DataApi cut() {
        log.info("开始{}切牌......", gameName);
        List<PokerBO> tempList = new LinkedList<>();
        int index = new Random().nextInt(pokerBox.size() - 2) + 1;
        tempList.addAll(pokerBox.subList(index, pokerBox.size()));
        tempList.addAll(pokerBox.subList(0, index));
        pokerBox = tempList;
        log.info("{}切牌完毕{}", gameName, JSON.toJSONString(pokerBox));
        return DataApi.generateSuccessMsg(String.format("%s切牌完毕", gameName));
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title deal
     * @Description 发牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:34:48
     * @Return
     **/
    public DataApi<Map<String, List<PokerBO>>> deal() {
        log.info("开始{}发牌......", gameName);
        Map<String, List<PokerBO>> pokerMap = new HashMap<>();
        for (int i = 0; i < holeCardBox.length; i++) { // 存放底牌
            int index = pokerBox.size() - 1 - i;
            holeCardBox[i] = pokerBox.get(index);
        }

        for (int i = holeCardBox.length; i < pokerBox.size(); i++) {
            PokerBO pokerCard = pokerBox.get(i);
            PokerPlayerInfoBO playBO = playerList.get(i % playerCount);
            playBO.getPokerBOList().add(pokerCard);
        }

        for (PokerPlayerInfoBO playerBO : playerList) {
            pokerMap.put(playerBO.getUserId(), playerBO.getPokerBOList());
        }

        if (holeCardBox != null) {
            pokerMap.put("holeCardList", Arrays.asList(holeCardBox));
        }
        log.info("{}发牌完毕：{}", gameName, JSON.toJSONString(pokerMap));
        return DataApi.generateSuccessMsg(0, String.format("%s发牌完毕", gameName), pokerMap);
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title sort
     * @Description 理牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:35:28
     * @Return
     **/
    public DataApi<Map<String, List<PokerBO>>> sort(String userId) {
        log.info("[{}]开始理牌......", userId);
        PokerPlayerInfoBO playerBO = playerList.stream().filter(player -> player.getUserId().equals(userId)).findFirst().get(); // 获取当前玩家的信息
        playerBO.getPokerBOList().sort(Comparator.comparing(PokerBO::getOrder)); // 扑克正序排序
        Map<String, List<PokerBO>> pokerMap = new HashMap<>();
        pokerMap.put("holeCardList", Arrays.asList(holeCardBox));
        log.info("[{}]理牌完毕：{}", gameName, JSON.toJSONString(pokerMap));
        return DataApi.generateSuccessMsg(0, String.format("[%s]理牌完毕", userId), pokerMap);
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title draw
     * @Description 摸牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:36:36
     * @Return
     **/
    public DataApi draw() {
        return null;
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title play
     * @Description 出牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:37:24
     * @Return
     **/
    public DataApi play() {
        return null;
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title disCard
     * @Description 弃牌
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:37:56
     * @Return
     **/
    public DataApi disCard() {
        return null;
    }

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
    public DataApi rule() {
        return null;
    }

    /**
     * @param
     * @throws
     * @ClassName Cards
     * @Title win
     * @Description 获胜判定
     * @Author 王俊元(wjysky520@gmail.com)
     * @Date 2022/08/31 22:38:20
     * @Return
     **/
    public DataApi win() {
        return null;
    }

    public int getIndex(String str) throws Exception {
        for (int i = 0; i < pipArray.length; i++) {
            if (StringUtils.isNoneBlank(str) && str.equals(pipArray[i])) {
                return i;
            }
        }
        throw new Exception();
    }
}
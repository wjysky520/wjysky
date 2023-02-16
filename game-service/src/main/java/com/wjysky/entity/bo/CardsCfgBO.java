package com.wjysky.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName : PokerCfgBo
 * @Description : 卡牌游戏业务实体类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 23:32:42
 */
@Getter
@Setter
public class CardsCfgBO {

    private String group; // 牌类游戏

    private String gameName; // 游戏名称

    private List<PlayerInfoBO> playerList; // 玩家列表

    private String gameClass; // 类别

    /* ------------------------------ 扑克 S ------------------------------ */
    private int pokerCount; // 扑克的副数

    private Boolean hasJoker; // 是否有大小鬼牌

    private int holeCardCount; // 底牌数量
    /* ------------------------------ 扑克 E ------------------------------ */
}
package com.wjysky.factory.cards.proc.poker;

import com.wjysky.factory.cards.proc.Poker;
import com.wjysky.pojo.bo.CardsCfgBO;
import com.wjysky.pojo.bo.poker.PokerBO;
import com.wjysky.pojo.bo.poker.PokerPlayerInfoBO;
import com.wjysky.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @ClassName : FightLandlord
 * @Description : 斗地主
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 22:54:49
 */
@Slf4j
public class FightLandlord extends Poker {

    public FightLandlord(CardsCfgBO cfgBo) {
        this.gameName = cfgBo.getGameName();
        this.playerCount = CollectionUtils.isNotEmpty(cfgBo.getPlayerList()) ? cfgBo.getPlayerList().size() : 0;
        this.playerList = cfgBo.getPlayerList().stream().map(player -> {
            PokerPlayerInfoBO pokerPlayer = ObjectUtil.convertBean(player, PokerPlayerInfoBO.class);
            pokerPlayer.setPokerBOList(new ArrayList<>());
            return new PokerPlayerInfoBO();
        }).collect(Collectors.toList()); // 玩家的
        this.pokerCount = cfgBo.getPokerCount();
        this.hasJoker = cfgBo.getHasJoker();
        this.holeCardBox = new PokerBO[cfgBo.getHoleCardCount()];
        this.pipArray = this.pipArray2;
    }
}
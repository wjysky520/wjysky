package com.wjysky.factory.cards.fact.impl;

import com.wjysky.factory.cards.Cards;
import com.wjysky.factory.cards.fact.IPokerFactory;
import com.wjysky.factory.cards.proc.poker.FightLandlord;
import com.wjysky.pojo.bo.CardsCfgBO;
import com.wjysky.pojo.enums.PokerClassEnum;
import org.springframework.stereotype.Component;

/**
 * @ClassName : CardFactoryImpl
 * @Description : 扑克类工厂实现类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 22:41:08
 */
@Component
public class PokerFactoryImpl implements IPokerFactory {

    @Override
    public Cards createGame(CardsCfgBO cfgBo) {
        PokerClassEnum categoryEnum = PokerClassEnum.getPokerClass(cfgBo.getGameClass());
        switch (categoryEnum) {
            case FIGHT_LANDLORD: // 斗地主
                return new FightLandlord(cfgBo);
        }
        return null;
    }
}
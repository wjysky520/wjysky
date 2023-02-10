package com.wjysky.factory.cards;

import com.wjysky.factory.cards.fact.IPokerFactory;
import com.wjysky.pojo.bo.CardsCfgBO;
import com.wjysky.pojo.enums.CardsGroupEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName : CardsFactoryImpl
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-01 00:37:07
 */
@Component
public class CardsFactoryImpl implements ICardsFactory {

    @Resource
    private IPokerFactory pokerFactory;

    @Override
    public Cards createGame(CardsCfgBO cfgBo) {
        CardsGroupEnum categoryEnum = CardsGroupEnum.getCardsGroup(cfgBo.getGroup());
        switch (categoryEnum) {
            case POKER: // 扑克
                return pokerFactory.createGame(cfgBo);
        }
        return null;
    }
}
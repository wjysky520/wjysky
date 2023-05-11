package com.wjysky.factory.cards.fact;

import com.wjysky.factory.cards.Cards;
import com.wjysky.pojo.bo.CardsCfgBO;

/**
 * @ClassName : ICardFactory
 * @Description : 扑克类工厂接口
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-08-31 22:40:46
 */
public interface IPokerFactory {

    public Cards createGame(CardsCfgBO cfgBo);
}
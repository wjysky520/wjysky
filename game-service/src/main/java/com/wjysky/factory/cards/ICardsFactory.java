package com.wjysky.factory.cards;

import com.wjysky.entity.bo.CardsCfgBO;

/**
 * @ClassName : ICardsFactory
 * @Description : TODO
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-01 00:32:15
 */
public interface ICardsFactory {

    public Cards createGame(CardsCfgBO cfgBo);
}
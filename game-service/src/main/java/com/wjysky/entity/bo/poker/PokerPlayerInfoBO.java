package com.wjysky.entity.bo.poker;

import com.wjysky.entity.bo.PlayerInfoBO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @ClassName : PokerPlayerInfoBO
 * @Description : 扑克玩家实体类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-06 23:08:49
 */
@Getter
@Setter
public class PokerPlayerInfoBO extends PlayerInfoBO {

    private List<PokerBO> pokerBOList;
}
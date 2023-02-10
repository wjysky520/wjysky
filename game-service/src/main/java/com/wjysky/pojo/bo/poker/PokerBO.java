package com.wjysky.pojo.bo.poker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName : PokerBO
 * @Description : 扑克实体类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-06 23:08:30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PokerBO {

    private int box; // 牌盒下标

    private String suit; // 花色

    private String pip; // 牌点

    private int order; // 扑克排序

    private int index; // 扑克下标
}
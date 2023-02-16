package com.wjysky.entity.enums;

/**
 * @ClassName : CardsGroupEnum
 * @Description : 牌类游戏大类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-01 00:39:29
 */
public enum CardsGroupEnum {

    POKER("poker", "扑克"),
    ;

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    CardsGroupEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CardsGroupEnum getCardsGroup(String code) {
        if (code == null || "".equals(code.trim())) {
            return null;
        }
        for (CardsGroupEnum c : CardsGroupEnum.values()) {
            if (code.equals(c.getCode())) {
                return c;
            }
        }
        return null;
    }
}
package com.wjysky.pojo.enums;

/**
 * @ClassName : PokerGameEnum
 * @Description : 扑克类游戏分类
 * @Author : 王俊元(wjysky520@gmail.com)
 * @Date : 2022-09-01 22:39:02
 */
public enum PokerClassEnum {

    FIGHT_LANDLORD("FIGHT_LANDLORD", "扑克"),
    ;

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    PokerClassEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PokerClassEnum getPokerClass(String code) {
        if (code == null || "".equals(code.trim())) {
            return null;
        }
        for (PokerClassEnum c : PokerClassEnum.values()) {
            if (code.equals(c.getCode())) {
                return c;
            }
        }
        return null;
    }
}
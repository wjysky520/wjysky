package com.wjysky.pojo.bo.reversi;

/**
 * @ClassName : Coordinate
 * @Description : TODO
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-01-29 16:29:50
 */
public class Coordinate {

    private int x;

    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
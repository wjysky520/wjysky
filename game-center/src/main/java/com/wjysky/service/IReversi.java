package com.wjysky.service;

import com.wjysky.pojo.bo.reversi.Coordinate;

import java.util.List;

/**
 * @ClassName : IReversi
 * @Description : TODO
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-01-29 16:28:14
 */
public interface IReversi {

    public void init();

    public List<Coordinate> checkPushPiece(String player);

    public List<Coordinate> pushPiece(int x, int y) throws Exception;

    public String checkVictory();
}
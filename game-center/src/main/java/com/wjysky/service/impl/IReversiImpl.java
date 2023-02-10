package com.wjysky.service.impl;

import com.alibaba.fastjson.JSON;
import com.wjysky.entity.bo.reversi.Coordinate;
import com.wjysky.service.IReversi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName : IReversiImpl
 * @Description : TODO
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-01-29 16:28:29
 */
@Service
@Slf4j
public class IReversiImpl implements IReversi {

    public static String[][] board = new String[8][8];

    private static String BLACK = "BLACK";

    private static String WHITE = "WHITE";

    private static String BLACK_PIECE = "O";

    private static String WHITE_PIECE = "●";

    private static String player = WHITE; // white-白棋，black-黑棋

    private static int whiteNum = 0;

    private static int blackNum = 0;

    private static List<Coordinate> whiteList = new ArrayList<>();

    private static List<Coordinate> blackList = new ArrayList<>();

//    public static void main(String[] args) {
//        init();
//        String victory;
//        while ((victory = checkVictory()) == null) { // 没有胜利者
//            try {
//                player = BLACK.equals(player) ? WHITE : BLACK;
//                String name = BLACK.equals(player) ? "黑棋玩家" : "白棋玩家";
//                if (checkPushPiece(player).size() <= 0) { // 检查当前用户是否可以落子
//                    continue;
//                }
//                System.out.print(String.format("玩家[{}]准备落子：", name));
//                Scanner input = new Scanner(System.in);
//                String push = input.next();
//                int x = Integer.valueOf(push.split(",")[0]);
//                int y = Integer.valueOf(push.split(",")[1]);
//                System.out.println(String.format("当前玩家[{}]落子坐标为[{}, {}]", name, x, y));
//                List<Coordinate> coordinateList = pushPiece(x, y);
//            } catch (Exception e) {
//                e.printStackTrace();
////                System.out.println(e.getMessage());
//                player = BLACK.equals(player) ? WHITE : BLACK;
//            }
//
//        }
//        if ("和棋".equals(victory)) {
//            System.out.println("双方和棋");
//        } else {
//            System.out.println("胜利者为：" + (BLACK.equals(player) ? "黑棋玩家" : "白棋玩家"));
//        }
//    }

    @Override
    public void init() {
        board[3][3] = WHITE_PIECE;
        board[3][4] = BLACK_PIECE;
        board[4][3] = BLACK_PIECE;
        board[4][4] = WHITE_PIECE;
        blackList.add(new Coordinate(3, 4));
        blackList.add(new Coordinate(4, 3));
        whiteList.add(new Coordinate(3, 3));
        whiteList.add(new Coordinate(4, 4));

//        board[1][0] = BLACK_PIECE;
//        board[1][1] = BLACK_PIECE;
//        board[0][0] = WHITE_PIECE;
//        board[0][1] = WHITE_PIECE;
//        blackList.add(new Coordinate(1, 0));
//        blackList.add(new Coordinate(1, 1));
//        whiteList.add(new Coordinate(0, 0));
//        whiteList.add(new Coordinate(0, 1));
        whiteNum = 2;
        blackNum = 2;
    }

    /**
     *
     * @ClassName Reversi
     * @Title checkPushPiece
     * @Description 确认是否可以落子
     * @param player 玩家
     * @Author 王俊元(wangjy1@belink.com)
     * @Date 2022/1/27 10:01
     * @Return java.util.List<Coordinate>
     * @throws
     **/
    @Override
    public List<Coordinate> checkPushPiece(String player) {
        String name = BLACK.equals(player) ? "黑棋玩家" : "白棋玩家";
        log.info("开始检查玩家[{}]是否可以落子%n", name);
//        String mychess = BLACK.equals(player) ? BLACK_PIECE : WHITE_PIECE;
//        String otherChess = WHITE.equals(player) ? BLACK_PIECE : WHITE_PIECE;
        List<Coordinate> myPieceList = BLACK.equals(player) ? blackList : whiteList;
        List<Coordinate> otherPieceList = WHITE.equals(player) ? blackList : whiteList;
        log.info("当前己方棋子共[{}]颗，坐标为：{}%n", myPieceList.size(), JSON.toJSONString(myPieceList));
        log.info("当前对方棋子共[{}]颗，坐标为：{}%n", otherPieceList.size(), JSON.toJSONString(otherPieceList));

        List<Coordinate> pushList = new ArrayList<>(); // 己方可落子的坐标集合
        myPieceList.forEach(myPiece -> {
            otherPieceList.forEach(otherPiece -> {
                int xTemp = otherPiece.getX() - myPiece.getX(); // 对方棋子在我方棋子的哪一列
                int yTemp = otherPiece.getY() - myPiece.getY(); // 对方棋子在我方棋子的哪一列
                if (xTemp >= -1 && xTemp <= 1 && yTemp >= -1 && yTemp <= 1) { // 己方棋子周围的八个方位中有对手棋子
                    // 在当前方向上搜索是否有符合规则的可放置己方棋子的坐标
                    for (int i = 1; myPiece.getX() + xTemp * i >= 0 && myPiece.getX() + xTemp * i < 8 && myPiece.getY() + yTemp * i >= 0 && myPiece.getY() + yTemp * i < 8; i++) {
                        int x = myPiece.getX() + xTemp * i;
                        int y = myPiece.getY() + yTemp * i;
                        if (StringUtils.isBlank(board[x][y])) {
                            pushList.add(new Coordinate(x, y));
                            break;
                        }
                    }
                }
            });
        });
        if (pushList.size() > 0) {
            log.info("玩家[{}]可以落子，可落子的坐标集合为：{}%n", name, JSON.toJSONString(pushList));
        } else {
            log.info("玩家[{}]不能落子%n", name);
        }
        return pushList;
    }

    /**
     *
     * @ClassName Reversi
     * @Title pushPiece
     * @Description 落子
     * @param x 落子的坐标，起始为0的第几排
     * @param y 落子的坐标，起始为0的第几列
     * @Author 王俊元(wangjy1@belink.com)
     * @Date 2022/1/12 11:57
     * @Return java.util.List<Coordinate>
     * @throws
     **/
    @Override
    public List<Coordinate> pushPiece(int x, int y) throws Exception {
        if (StringUtils.isNotBlank(board[x][y])) { // 落子的坐标已有棋子
            throw new Exception(String.format("落子不符合规则，坐标[{}][{}]已有棋子", x, y));
        }
        List<Coordinate> coordinateList = new ArrayList<>();
        String mychess = BLACK.equals(player) ? BLACK_PIECE : WHITE_PIECE; // 当前落子方所执棋子
        String otherChess = WHITE.equals(player) ? BLACK_PIECE : WHITE_PIECE; // 当前落子方的对手所执棋子
        // 外层双循环用来定位落子坐标周围8个方位的坐标
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                List<Coordinate> tempList = new ArrayList<>();
                if (x + i < 0 || x + i > 7 || y + j < 0 || y + j > 7 || (i == 0 && j == 0)) { // 如果坐标跳出棋盘则忽略该坐标
                    continue;
                }
//                System.out.println(String.format("当前检查坐标[{}][{}]位置的棋子[{}]", x + i, y + j, board[x + i][y + j]));
                if (StringUtils.isNotBlank(board[x + i][y + j]) && otherChess.equals(board[x + i][y + j])) { // 如果当前坐标为对手棋子
                    // 循环当前方向上的所有坐标直到棋盘边缘
                    for (int k = 1; x + i * k >= 0 && x + i * k < 8 && y + j * k >= 0 && y + j * k < 8; k++) {
                        int m = x + i * k;
                        int n = y + j * k;
                        // 如果当前方位上有己方棋子可以包夹对手棋子则将临时集合（对方棋子的坐标）中的所有坐标添加到需翻转的棋子结合中
                        if (!StringUtils.isBlank(board[m][n]) && mychess.equals(board[m][n])) {
                            coordinateList.addAll(tempList);
                            break;
                        }
                        tempList.add(new Coordinate(m, n)); // 将当前方向上的棋子坐标放入临时集合中
                    }
                }
            }
        }
        if (coordinateList.size() <= 0) {
//            System.out.println(String.format("落子不符合规则，没有可被翻转的[{}]", BLACK.equals(player) ? "白棋" : "黑棋"));
            throw new Exception(String.format("落子不符合规则，没有可被翻转的[{}]", BLACK.equals(player) ? "白棋" : "黑棋"));
        } else {
            log.info("当前被翻转的[{}]坐标集合为：{}%n", BLACK.equals(player) ? "白棋" : "黑棋", JSON.toJSONString(coordinateList));
            whiteNum = WHITE.equals(player) ? whiteNum + coordinateList.size() + 1 : whiteNum - coordinateList.size(); // 白棋根据玩家执棋颜色加或减相应的翻转数量
            blackNum = BLACK.equals(player) ? blackNum + coordinateList.size() + 1 : blackNum - coordinateList.size(); // 黑棋根据玩家执棋颜色加或减相应的翻转数量
            coordinateList.add(new Coordinate(x, y)); // 棋子需加上当前的落子
            coordinateList.forEach(coordinate -> board[coordinate.getX()][coordinate.getY()] = mychess); // 翻转对应的对手棋子为己方棋子
            if (WHITE.equals(player)) { // 当前玩家执白棋时
                whiteList.addAll(coordinateList); // 白棋集合添加被翻转的棋子和当前落的棋子
                // 黑棋集合删除被翻转的棋子
                blackList = blackList.stream().filter(black -> coordinateList.stream().noneMatch(coordinate -> black.getX() == coordinate.getX() && black.getY() == coordinate.getY())).collect(Collectors.toList());
            } else { // 当前玩家执黑棋时
                blackList.addAll(coordinateList); // 黑棋集合添加被翻转的棋子和当前落的棋子
                // 白棋集合删除被翻转的棋子
                whiteList = whiteList.stream().filter(white -> coordinateList.stream().noneMatch(coordinate -> white.getX() == coordinate.getX() && white.getY() == coordinate.getY())).collect(Collectors.toList());
            }
        }
        return coordinateList;
    }

    /**
     *
     * @ClassName Reversi
     * @Title checkVictory
     * @Description 判断是否有胜利方
     * @param
     * @Author 王俊元(wangjy1@belink.com)
     * @Date 2022/1/27 10:01
     * @Return java.lang.String
     * @throws
     **/
    @Override
    public String checkVictory() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        printBoard();
        log.info("当前黑棋有[{}]颗，白棋有[{}]颗%n", blackNum, whiteNum);
        if (whiteNum == 0) { // 当前棋盘白棋数量为0，则执黑棋玩家胜利
            return BLACK;
        }
        if (blackNum == 0) { // 当前棋盘黑棋数量为0，则执白棋玩家胜利
            return WHITE;
        }
        if (checkPushPiece(BLACK).size() <= 0 && checkPushPiece(WHITE).size() <= 0) { // 如果双方都不能再次落子则比较棋子数量来确认胜利者
            if (whiteNum > blackNum) { // 如果白棋数量大于黑棋则执白棋玩家胜利
                return WHITE;
            } else if (whiteNum < blackNum) { // 如果黑棋数量大于白棋则执黑棋玩家胜利
                return BLACK;
            } else { // 如果双方棋子数量相等则为和棋
                return "和棋";
            }
        }
        return null;
    }

    /**
     *
     * @ClassName Reversi
     * @Title printBoard
     * @Description 打印棋盘
     * @param
     * @Author 王俊元(wangjy1@belink.com)
     * @Date 2022/1/27 10:02
     * @Return void
     * @throws
     **/
    private static void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = board[i][j];
                if (StringUtils.isBlank(piece)) {
                    if (i == 0 && j == 0) {
                        System.out.print("┌");
                    } else if (i == 0 && j == 7) {
                        System.out.print("┐");
                    } else if (i == 7 && j == 0) {
                        System.out.print("└");
                    } else if (i == 7 && j == 7) {
                        System.out.print("┘");
                    } else if (i == 0) {
                        System.out.print("┬");
                    } else if (i == 7) {
                        System.out.print("┴");
                    } else if (j == 0) {
                        System.out.print("├");
                    } else if (j == 7) {
                        System.out.print("┤");
                    } else {
                        System.out.print("┼");
                    }
                } else {
                    System.out.print(piece);
                }
                if (j < 7) {
                    System.out.print("─");
                }
            }
            System.out.println();
        }
//        for (String[] board : board) {
//            StringBuilder content = new StringBuilder();
//            for (String piece : board) {
//                content.append(StringUtils.isBlank(piece) ? "+-" : piece + "-");
//            }
//            content.substring(0, content.length() - 1);
//            System.out.println(content);
//        }
    }
}
package com.wjysky.components.idgenerator;

import cn.hutool.core.lang.Snowflake;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * IdGenerator
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-05-11 17:36:21
 * @apiNote 基于hutool实现雪花算法类id生成器
 */
public class IdGenerator {

    /**
     * 四位随机数
     */
    private static final int RD_4 = 9999;

    /**
     * 随机类
     */
    private static final Random R = new Random();

    /**
     * 基于hutool实现雪花算法类
     */
    private final Snowflake snowflake;

    private final MySnowflake mySnowflake;

    public IdGenerator(Snowflake snowflake,MySnowflake mySnowflake) {
        this.snowflake = snowflake;
        this.mySnowflake = mySnowflake;
    }

    /**
     * @bizName 生成雪花算法id
     *
     * @title getId
     * @apiNote 基于hutool实现雪花算法类，由64bit组成
     *          1位符号位（0表示正，1表示负） + 41位时间戳 + 10位机器ID（5位机房ID + 5位机器ID） + 12位序列号（可允许同一毫秒生成4096个ID）
     * @param
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/5/11 17:37
     * @return long
     **/
    public synchronized long getId() {
        try {
            TimeUnit.MILLISECONDS.sleep(5);
            return getId(RD_4);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return getId(RD_4);
        }
    }

    /**
     * @bizName 生成雪花算法id
     *
     * @title getId
     * @apiNote 基于hutool实现雪花算法类
     * @param factor 随机因子，4位随机数
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/5/11 17:43
     * @return long
     **/
    private long getId(int factor) {
        return snowflake.nextId() + getRandom(factor);
    }

    /**
     * @bizName 生成4位随机数
     *
     * @title getRandom
     * @apiNote 生成1000~9999的long类型
     * @param factor 随机因子
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2023/5/11 17:50
     * @return long
     **/
    private static long getRandom(int factor) {
        if (factor < 1000) {
            factor = 1000;
        } else if (factor > 8999) {
            factor = 8999;
        }
        return R.nextInt(factor) + 1000;
    }

    /**
     * 生成雪花算法id
     *
     * @return long
     */
    public synchronized long getSnowId() {
        try {
            TimeUnit.MILLISECONDS.sleep(5);
            return getSnowId(RD_4);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return getSnowId(RD_4);
        }
    }

    private long getSnowId(int factor){
        return mySnowflake.nextId() + getRandom(factor);
    }

}


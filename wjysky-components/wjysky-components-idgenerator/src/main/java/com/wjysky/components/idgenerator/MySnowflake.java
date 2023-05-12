package com.wjysky.components.idgenerator;

import cn.hutool.core.lang.Snowflake;

/**
 * 基于雪花算法的类实现
 *
 * @author yunshaowei@talkweb.com.cn
 * @date 2023/3/8
 * @apiNote 基于雪花算法的类实现
 */
public class MySnowflake extends Snowflake {

    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStmp = -1L;

    public MySnowflake(long workerId, long datacenterId) {
        super(workerId, datacenterId);
    }

    public MySnowflake(long workerId, long datacenterId, boolean isUseSystemClock) {
        super(workerId, datacenterId, isUseSystemClock);
    }

    @Override
    public synchronized long nextId() {
        long currStmp = this.getNewstmp();
        if (currStmp < this.lastStmp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        } else {
            if (currStmp == lastStmp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    currStmp = this.getNextMill();
                }
            } else {
                this.sequence = 0L;
            }
            this.lastStmp = currStmp;
            return currStmp - 1420103747000L << 11
                    | this.datacenterId << 17
                    | this.machineId << 12
                    | this.sequence;
        }
    }

    private long getNextMill(){
        long mill;
        for (mill = this.getNewstmp();mill<=this.lastStmp;mill=this.getNewstmp()){
        }
        return mill;
    }

    private long getNewstmp(){
        return System.currentTimeMillis();
    }
}

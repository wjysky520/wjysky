package com.wjysky.framework.dao.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * EcmMybatisMapper接口
 *
 * @param <T> 泛型
 * @author duanzhijun24470@talkweb.com.cn
 * @date 2022/04/28
 * @apiNote EcmMybatisMapper接口, 支持泛型
 */
public interface WjyMybatisMapper<T> extends BaseMapper<T> {

    int BATCH_SIZE = 500; // 批处理处理条数

    /**
     * 功能描述：批量插入接口 走batch模式
     *
     * @param t 插入对像
     * @return java.lang.Integer 返回影响行数
     * @author yinhualong@talkweb.com.cn
     * @date 2022/6/30
     */
    default Integer insertBatch(T t) {
        throw new UnsupportedOperationException("请实现批量插入接口");
    }

    /**
     * 功能描述：批量更新接口 走batch模式
     *
     * @param t 插入对像
     * @return java.lang.Integer 返回影响行数
     * @author yinhualong@talkweb.com.cn
     * @date 2022/6/30
     */
    default Integer updateBatch(T t) {
        throw new UnsupportedOperationException("请实现批量更新接口");
    }

    /**
     * @bizName 批量插入
     *
     * @title insertBatchSomeColumn
     * @apiNote insertBatchSomeColumn方法是利用foreach标签拼接SQL实现的，使用时需注意不要超出执行的上限条数
     * @param dataList 批量插入的数据集合
     * @author 王俊元（wangjunyuan@talkweb.com.cn）
     * @date 2022/12/10 0:42
     * @return java.lang.Integer
     **/
    Integer insertBatchSomeColumn(List<T> dataList);
}

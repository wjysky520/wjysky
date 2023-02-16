package com.wjysky.framework.dao.handler;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 元数据自动填充处理类
 *
 * @author duanzhijun24470@talkweb.com.cn
 * @date 2022/04/28
 * @apiNote 元数据自动填充处理类
 */
@Slf4j
@Component
public class MetaFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject obj) {
        if (permitFill(obj)) {
            this.strictInsertFill(obj, "createTime", LocalDateTime.class, LocalDateTime.now());
            this.strictInsertFill(obj, "deleteFlag", Integer.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject obj) {
        if (permitFill(obj)) {
            this.strictUpdateFill(obj, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }
    }

    /**
     * 判断是否自动跳过
     *
     * @param metaObject 实体对象
     * @return 判断是否自动跳过
     */
    private boolean permitFill(MetaObject metaObject) {
        FieldFill fieldFill = (FieldFill) getFieldValByName("fieldFill", metaObject);
        return fieldFill != FieldFill.DEFAULT;
    }
}

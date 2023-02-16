package com.wjysky.framework.dao.base.mapper;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * MybatisPlusConfig
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2022-12-05 18:19:10
 * @apiNote 添加SQL操作方法
 */
public class WjySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加InsertBatchSomeColumn
        methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.INSERT_UPDATE));
        return methodList;
    }
}

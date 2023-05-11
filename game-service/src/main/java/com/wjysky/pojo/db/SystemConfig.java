package com.wjysky.pojo.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wjysky.framework.dao.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * SystemConfig
 *
 * @author 王俊元（wangjunyuan@talkweb.com.cn）
 * @date 2023-02-16 18:18:07
 * @apiNote
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@TableName("system_config")
public class SystemConfig extends BaseEntity {

    @TableField("name")
    private String name; // 配置名称

    @TableField("nid")
    private String nid; // 配置唯一标识

    @TableField("value")
    private String value; // 配置值

    @TableField("detail")
    private String detail; // 配置说明

    @TableField("deleted")
    private Integer deleted; // 是否删除，0-未删除，1-已删除

    @TableField("status")
    private Integer status; // 是否同步，0-未同步，1-已同步
}
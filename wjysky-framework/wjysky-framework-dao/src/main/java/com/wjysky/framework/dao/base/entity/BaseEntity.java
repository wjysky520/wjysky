package com.wjysky.framework.dao.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author yinhualong@talkweb.com.cn
 * @date 2022/03/11
 * @apiNote 基础实体类，提供一些共用的属性 如：创建时间，更新时间，数据版本号
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
     /**
     * 主键id
     */
    @TableId("id")
    private Long id;

//    /**
//     * 创建时间
//     */
//    @TableField(value = "create_time", fill = FieldFill.INSERT)
//    private LocalDateTime createTime;
//
//    /**
//     * 创建人
//     */
//    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
//    private Long createUserId;
//
//    /**
//     * 创建人
//     */
//    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
//    private String createUserName;
//
//    /**
//     * 更新人
//     */
//    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
//    private Long updateUserId;
//
//    /**
//     * 更新人
//     */
//    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
//    private String updateUserName;
//
//    /**
//     * 更新时间
//     */
//    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
//    private LocalDateTime updateTime;
//
//    /**
//     * 删除标识
//     */
//    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
//    private Integer deleteFlag;
//
//    /**
//     * 是否需要自动设置,默认是,手动设置 FieldFill.DEFAULT
//     */
//    @TableField(exist = false)
//    @JsonIgnore
//    private FieldFill fieldFill = FieldFill.INSERT;
//
//    /**
//     * 关闭自动设置
//     */
//    public void disableFill(){
//        this.fieldFill = FieldFill.DEFAULT;
//    }
}

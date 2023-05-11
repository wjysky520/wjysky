package com.wjysky.pojo.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @ClassName : SysCfg
 * @Description : 系统配置表
 * @Author : 王俊元(www.wjysky.com)
 * @Date : 2022-10-13 17:41:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_cfg")
@SuperBuilder(toBuilder = true)
public class SysCfg extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String nid;

    private String value;

    private String description;

    private String deleted;

    private String status;
}

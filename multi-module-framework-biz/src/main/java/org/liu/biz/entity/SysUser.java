package org.liu.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类（适配JDK17，使用LocalDateTime替代Date）
 */
@Data
@TableName("sys_user") // 数据库表名
public class SysUser {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 创建时间（JDK17 LocalDateTime）
     */
    private LocalDateTime createTime;

    /**
     * 用户状态（关联api模块枚举）
     */
    private Integer status;
}
package org.liu.core.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户Entity（对应表：sys_user，原有框架类，已从biz迁到core）
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long id;          // 用户ID（主键）
    private String userName;  // 用户名
    private String phone;     // 手机号（唯一）
    private String password;  // 加密密码
    private Integer status;   // 用户状态（0正常，1禁用）
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
}
package org.liu.core.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.liu.core.entity.user.SysUser;
import org.springframework.stereotype.Repository;

/**
 * 系统用户Mapper（原有框架类，复用）
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    // 按手机号查询用户
    SysUser selectByPhone(String phone);
}
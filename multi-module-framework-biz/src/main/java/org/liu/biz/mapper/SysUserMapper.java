package org.liu.biz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.liu.biz.entity.SysUser;
import org.liu.core.mapper.BaseCoreMapper;

/**
 * 用户Mapper（继承核心抽象Mapper）
 */
@Mapper
public interface SysUserMapper extends BaseCoreMapper<SysUser> {
    // 自定义SQL（可选）
    SysUser selectByPhone(String phone);
}
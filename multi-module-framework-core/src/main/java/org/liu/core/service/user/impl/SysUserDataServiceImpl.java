package org.liu.core.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.liu.core.entity.user.SysUser;
import org.liu.core.mapper.user.SysUserMapper;
import org.liu.core.service.user.SysUserDataService;
import org.springframework.stereotype.Service;

/**
 * 系统用户数据Service实现（原有框架类，复用）
 */
@Service
public class SysUserDataServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserDataService {

    @Override
    public SysUser getByPhone(String phone) {
        return baseMapper.selectByPhone(phone);
    }
}
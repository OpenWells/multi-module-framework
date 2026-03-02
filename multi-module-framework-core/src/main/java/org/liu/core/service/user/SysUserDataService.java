package org.liu.core.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import org.liu.core.entity.user.SysUser;

/**
 * 系统用户-数据层Service（纯CRUD，无业务逻辑，原有框架类，复用）
 */
public interface SysUserDataService extends IService<SysUser> {
    // 按手机号查询用户
    SysUser getByPhone(String phone);
}
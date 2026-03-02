package org.liu.biz.service.user;

import org.liu.api.dto.user.UserLoginDTO;
import org.liu.api.vo.user.UserLoginVO;

/**
 * 系统用户-业务层Service（核心业务逻辑，原有框架类，复用）
 */
public interface SysUserBizService {
    // 用户登录
    UserLoginVO login(UserLoginDTO dto);

    // 用户注册（带事务）
    void register(UserLoginDTO dto);

    // 校验用户是否有效（秒杀/其他业务调用）
    boolean validateUser(Long userId);
}
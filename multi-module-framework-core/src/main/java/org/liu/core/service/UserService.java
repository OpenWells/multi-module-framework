package org.liu.core.service;

import org.liu.api.dto.UserDTO;
import org.liu.api.vo.UserVO;

/**
 * 用户服务核心接口（仅定义，不实现）
 */
public interface UserService extends BaseService<UserDTO, UserVO, Long> {
    /**
     * 根据手机号查询用户
     */
    UserVO getByPhone(String phone);
}

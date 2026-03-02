package org.liu.biz.service.user.impl;

import jakarta.annotation.Resource;
import org.liu.api.dto.user.UserLoginDTO;
import org.liu.api.vo.user.UserLoginVO;
import org.liu.biz.service.user.SysUserBizService;
import org.liu.common.constant.UserConstant;
import org.liu.common.exception.BizException;
import org.liu.common.util.PasswordUtil;
import org.liu.common.util.TokenUtil;
import org.liu.core.entity.user.SysUser;
import org.liu.core.service.user.SysUserDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 系统用户业务Service实现（原有框架类，复用，事务仅在此层控制）
 */
@Service
public class SysUserBizServiceImpl implements SysUserBizService {

    @Resource
    private SysUserDataService sysUserDataService;
    @Resource
    private PasswordUtil passwordUtil;
    @Resource
    private TokenUtil tokenUtil;

    @Override
    public UserLoginVO login(UserLoginDTO dto) {
        // 1. 纯数据操作：按手机号查用户（调用core层DataService）
        SysUser sysUser = sysUserDataService.getByPhone(dto.getPhone());
        if (sysUser == null) {
            throw new BizException("用户不存在");
        }
        // 2. 业务逻辑：校验用户状态
        if (!UserConstant.USER_STATUS_NORMAL.equals(sysUser.getStatus())) {
            throw new BizException("用户已禁用");
        }
        // 3. 业务逻辑：校验密码
        if (!passwordUtil.matches(dto.getPassword(), sysUser.getPassword())) {
            throw new BizException("密码错误");
        }
        // 4. 业务逻辑：生成Token
        String token = tokenUtil.generateToken(sysUser.getId().toString());
        // 5. 封装VO（不返回Entity）
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(sysUser.getId());
        vo.setUserName(sysUser.getUserName());
        vo.setToken(token);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserLoginDTO dto) {
        // 1. 业务逻辑：校验手机号是否已注册
        if (sysUserDataService.getByPhone(dto.getPhone()) != null) {
            throw new BizException("手机号已注册");
        }
        // 2. 业务逻辑：密码加密
        String encryptPwd = passwordUtil.encrypt(dto.getPassword());
        // 3. 封装Entity（仅biz层内部使用）
        SysUser sysUser = new SysUser();
        sysUser.setUserName(dto.getUserName());
        sysUser.setPhone(dto.getPhone());
        sysUser.setPassword(encryptPwd);
        sysUser.setStatus(UserConstant.USER_STATUS_NORMAL);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        // 4. 纯数据操作：保存用户
        sysUserDataService.save(sysUser);
    }

    @Override
    public boolean validateUser(Long userId) {
        // 1. 纯数据操作：按ID查用户
        SysUser sysUser = sysUserDataService.getById(userId);
        // 2. 业务逻辑：判断用户是否存在且状态正常
        return sysUser != null && UserConstant.USER_STATUS_NORMAL.equals(sysUser.getStatus());
    }
}
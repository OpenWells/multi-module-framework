package org.liu.biz.service.impl;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.liu.api.dto.UserDTO;
import org.liu.api.vo.UserVO;
import org.liu.biz.entity.SysUser;
import org.liu.biz.mapper.SysUserMapper;
import org.liu.common.exception.BusinessException;
import org.liu.common.util.RedisUtil;
import org.liu.core.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * 整合Redis缓存的用户服务实现
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisUtil redisUtil;

    // 缓存前缀（JDK17常量）
    private static final String USER_CACHE_PREFIX = "sys:user:";
    // 缓存过期时间（5分钟）
    private static final long USER_CACHE_EXPIRE = 5L;

    @Override
    public UserVO getById(Long id) {
        log.info("【用户服务】根据ID查询用户：{}", id);
        // 1. 先查缓存
        String cacheKey = USER_CACHE_PREFIX + id;
        UserVO cacheUser = redisUtil.get(cacheKey);
        if (cacheUser != null) {
            log.info("【用户服务】从缓存获取用户：{}", id);
            return cacheUser;
        }

        // 2. 缓存未命中，查数据库
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }

        // 3. 转换为VO并放入缓存
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(sysUser, userVO);
        userVO.setPhone("%s****%s".formatted(sysUser.getPhone().substring(0,3), sysUser.getPhone().substring(7)));
        userVO.setCreateTime(sysUser.getCreateTime().toString());

        // 放入缓存（5分钟过期）
        redisUtil.set(cacheKey, userVO, USER_CACHE_EXPIRE, TimeUnit.MINUTES);
        log.info("【用户服务】从数据库获取用户并放入缓存：{}", id);
        return userVO;
    }

    @Override
    @Transactional // 事务注解（Spring Boot3适配）
    public boolean save(UserDTO dto) {
        log.info("【用户服务】新增用户：{}", dto);
        // 分布式锁（防止重复提交，JDK17 UUID生成）
        String lockKey = "sys:user:lock:" + dto.getPhone();
        String requestId = IdUtil.simpleUUID(); // Hutool工具类，适配JDK17
        try {
            boolean lockSuccess = redisUtil.tryLock(lockKey, requestId, 30);
            if (!lockSuccess) {
                throw new BusinessException("请勿重复提交");
            }

            // 新增用户
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(dto, sysUser);
            sysUser.setCreateTime(LocalDateTime.now());
            sysUser.setStatus(1);
            boolean saveSuccess = sysUserMapper.insert(sysUser) > 0;

            // 新增成功后删除缓存（若有）
            if (saveSuccess) {
                redisUtil.delete(USER_CACHE_PREFIX + sysUser.getId());
            }
            return saveSuccess;
        } finally {
            // 释放锁
            redisUtil.releaseLock(lockKey, requestId);
        }
    }

    // 其余方法（update/deleteById/getByPhone）同理，操作数据库后更新/删除缓存
    @Override
    public boolean update(UserDTO dto) {

        // 1. 更新数据库
        // 2. 删除缓存
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        // 1. 删除数据库
        boolean deleteSuccess = sysUserMapper.deleteById(id) > 0;
        // 2. 删除缓存
        if (deleteSuccess) {
            redisUtil.delete(USER_CACHE_PREFIX + id);
        }
        return deleteSuccess;
    }

    @Override
    public UserVO getByPhone(String phone) {
        // 同理，先查缓存，再查数据库
        return null;
    }
}
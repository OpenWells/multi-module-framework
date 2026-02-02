package org.liu.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * 通用Mapper抽象（适配JDK17，继承MyBatis-Plus BaseMapper）
 * @param <T> 实体类类型
 */
public interface BaseCoreMapper<T> extends BaseMapper<T> {
    // 扩展通用方法（可选）
    T selectByBizId(Serializable bizId);
}
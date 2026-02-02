package org.liu.core.service;

import java.io.Serializable;

/**
 * 通用CRUD服务抽象（JDK17泛型适配）
 * @param <T> DTO类型
 * @param <VO> VO类型
 * @param <ID> 主键类型
 */
public interface BaseService<T, VO, ID extends Serializable> {
    /**
     * 根据ID查询
     */
    VO getById(ID id);

    /**
     * 新增
     */
    boolean save(T dto);

    /**
     * 修改
     */
    boolean update(T dto);

    /**
     * 删除
     */
    boolean deleteById(ID id);
}
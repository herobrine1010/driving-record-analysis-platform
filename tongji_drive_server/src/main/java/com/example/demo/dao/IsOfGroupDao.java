package com.example.demo.dao;

import com.example.demo.entity.IsOfGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Repository;
/**
 * (IsOfGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
@Mapper
@Repository
public interface IsOfGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    IsOfGroup queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<IsOfGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param isOfGroup 实例对象
     * @return 对象列表
     */
    List<IsOfGroup> queryAll(IsOfGroup isOfGroup);

    /**
     * 新增数据
     *
     * @param isOfGroup 实例对象
     * @return 影响行数
     */
    int insert(IsOfGroup isOfGroup);

    /**
     * 修改数据
     *
     * @param isOfGroup 实例对象
     * @return 影响行数
     */
    int update(IsOfGroup isOfGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
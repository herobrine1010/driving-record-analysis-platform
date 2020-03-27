package com.example.demo.dao;

import com.example.demo.entity.HasGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Repository;
/**
 * (HasGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
@Mapper
@Repository
public interface HasGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    HasGroup queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<HasGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param hasGroup 实例对象
     * @return 对象列表
     */
    List<HasGroup> queryAll(HasGroup hasGroup);

    /**
     * 新增数据
     *
     * @param hasGroup 实例对象
     * @return 影响行数
     */
    int insert(HasGroup hasGroup);

    /**
     * 修改数据
     *
     * @param hasGroup 实例对象
     * @return 影响行数
     */
    int update(HasGroup hasGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
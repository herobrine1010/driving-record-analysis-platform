package com.example.demo.dao;

import com.example.demo.entity.RaisesApply;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (RaisesApply)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public interface RaisesApplyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RaisesApply queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<RaisesApply> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param raisesApply 实例对象
     * @return 对象列表
     */
    List<RaisesApply> queryAll(RaisesApply raisesApply);

    /**
     * 新增数据
     *
     * @param raisesApply 实例对象
     * @return 影响行数
     */
    int insert(RaisesApply raisesApply);

    /**
     * 修改数据
     *
     * @param raisesApply 实例对象
     * @return 影响行数
     */
    int update(RaisesApply raisesApply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
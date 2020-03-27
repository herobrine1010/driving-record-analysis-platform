package com.example.demo.dao;

import com.example.demo.entity.Records;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Records)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public interface RecordsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Records queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Records> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param records 实例对象
     * @return 对象列表
     */
    List<Records> queryAll(Records records);

    /**
     * 新增数据
     *
     * @param records 实例对象
     * @return 影响行数
     */
    int insert(Records records);

    /**
     * 修改数据
     *
     * @param records 实例对象
     * @return 影响行数
     */
    int update(Records records);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
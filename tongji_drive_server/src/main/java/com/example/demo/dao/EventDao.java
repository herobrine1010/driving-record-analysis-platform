package com.example.demo.dao;

import com.example.demo.entity.Event;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Event)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public interface EventDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Event queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Event> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param event 实例对象
     * @return 对象列表
     */
    List<Event> queryAll(Event event);

    /**
     * 新增数据
     *
     * @param event 实例对象
     * @return 影响行数
     */
    int insert(Event event);

    /**
     * 修改数据
     *
     * @param event 实例对象
     * @return 影响行数
     */
    int update(Event event);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}
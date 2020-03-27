package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-05 20:54:05
 */
@Mapper
@Repository
public interface UserDao {

    User queryById(Integer id);

    List<User> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<User> queryAll(User user);

    int insert(User user);

    int update(User user);

    int deleteById(Integer id);




}
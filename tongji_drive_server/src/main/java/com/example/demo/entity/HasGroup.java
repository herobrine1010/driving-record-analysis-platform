package com.example.demo.entity;

import java.io.Serializable;

/**
 * (HasGroup)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class HasGroup implements Serializable {
    private static final long serialVersionUID = 200963942256787189L;
    
    private Integer id;
    
    private Integer userId;
    
    private Integer groupId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
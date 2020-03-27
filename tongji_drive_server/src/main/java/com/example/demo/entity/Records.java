package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Records)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class Records implements Serializable {
    private static final long serialVersionUID = -90692171487239469L;
    
    private Integer id;
    
    private Integer recordId;
    
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
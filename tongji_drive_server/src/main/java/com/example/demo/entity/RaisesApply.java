package com.example.demo.entity;

import java.io.Serializable;

/**
 * (RaisesApply)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class RaisesApply implements Serializable {
    private static final long serialVersionUID = 136253190589320966L;
    
    private Integer id;
    
    private Integer applyId;
    
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
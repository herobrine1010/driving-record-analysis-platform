package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Apply)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class Apply implements Serializable {
    private static final long serialVersionUID = -32871851671116366L;
    
    private Integer id;
    
    private Integer status;
    
    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
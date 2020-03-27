package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Group)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class Group implements Serializable {
    private static final long serialVersionUID = 351197398253531279L;
    
    private Integer id;
    
    private String name;
    
    private String info;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
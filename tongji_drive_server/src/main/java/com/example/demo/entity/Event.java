package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Event)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class Event implements Serializable {
    private static final long serialVersionUID = -35323535637472688L;
    
    private Integer id;
    
    private String timestamp;
    
    private String longitude;
    
    private String latitude;
    
    private String comment;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
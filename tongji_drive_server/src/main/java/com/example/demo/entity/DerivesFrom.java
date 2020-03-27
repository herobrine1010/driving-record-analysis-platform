package com.example.demo.entity;

import java.io.Serializable;

/**
 * (DerivesFrom)实体类
 *
 * @author makejava
 * @since 2020-03-05 20:54:04
 */
public class DerivesFrom implements Serializable {
    private static final long serialVersionUID = -95121118092676997L;
    
    private Integer id;
    
    private Integer recordId;
    
    private Integer eventId;


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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

}
package com.example.demo.entity;

import java.io.Serializable;

/**
 * (Record)实体类
 *
 * @author makejava
 * @since 2020-03-07 14:42:56
 */
public class Record implements Serializable {
    private static final long serialVersionUID = -26978074305200573L;
    
    private Integer id;
    
    private String alias;
    
    private String csvPath;
    
    private String video1Path;
    
    private String video2Path;
    
    private Object rating;
    
    private String comment;
    
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCsvPath() {
        return csvPath;
    }

    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }

    public String getVideo1Path() {
        return video1Path;
    }

    public void setVideo1Path(String video1Path) {
        this.video1Path = video1Path;
    }

    public String getVideo2Path() {
        return video2Path;
    }

    public void setVideo2Path(String video2Path) {
        this.video2Path = video2Path;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
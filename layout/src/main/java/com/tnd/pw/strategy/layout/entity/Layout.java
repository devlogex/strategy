package com.tnd.pw.strategy.layout.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Layout implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("parent_id")
    private Long parentId;
    @SerializedName("type")
    private String type;
    @SerializedName("layout")
    private String layout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }
}

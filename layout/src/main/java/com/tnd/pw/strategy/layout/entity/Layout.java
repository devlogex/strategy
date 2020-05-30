package com.tnd.pw.strategy.layout.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Layout implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("workspace_id")
    private Long workspaceId;
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

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
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

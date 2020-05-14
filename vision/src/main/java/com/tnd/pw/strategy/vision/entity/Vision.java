package com.tnd.pw.strategy.vision.entity;

import com.google.gson.annotations.SerializedName;

public class Vision extends BaseEntity{

    @SerializedName("workspace_id")
    private Long workspaceId;

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }
}

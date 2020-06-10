package com.tnd.pw.strategy.model.entity;

import com.google.gson.annotations.SerializedName;

public class Model extends BaseEntity{
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("type")
    private Integer type;
    @SerializedName("buz_type")
    private String buzType;
    @SerializedName("time_frame")
    private String timeFrame;

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getBuzType() {
        return buzType;
    }

    public void setBuzType(String buzType) {
        this.buzType = buzType;
    }
}

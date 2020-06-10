package com.tnd.pw.strategy.positioning.entity;

import com.google.gson.annotations.SerializedName;

public class Position extends BaseEntity {
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("buz_type")
    private String buzType;
    @SerializedName("time_frame")
    private String timeFrame;

    public String getBuzType() {
        return buzType;
    }

    public void setBuzType(String buzType) {
        this.buzType = buzType;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }
}

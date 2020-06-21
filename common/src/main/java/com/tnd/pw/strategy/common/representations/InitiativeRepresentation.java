package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiativeRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("parent_initiative")
    private String parentInitiative;
    @SerializedName("status")
    private String status;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("color")
    private String color;
    @SerializedName("start_at")
    private Long startAt;
    @SerializedName("end_at")
    private Long endAt;
    @SerializedName("visible")
    private Integer visible;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getParentInitiative() {
        return parentInitiative;
    }

    public void setParentInitiative(String parentInitiative) {
        this.parentInitiative = parentInitiative;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }
}

package com.tnd.pw.strategy.common.requests;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VisionRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("vision_id")
    private String visionId;
    @SerializedName("workspace_id")
    private String workspaceId;
    @SerializedName("files")
    private String files;

    @SerializedName("component_id")
    private String componentId;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;

    public String getVisionId() {
        return visionId;
    }

    public void setVisionId(String visionId) {
        this.visionId = visionId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

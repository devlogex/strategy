package com.tnd.pw.strategy.common.requests;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StrategyRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("vision_id")
    private Long visionId;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("files")
    private String files;

    @SerializedName("component_id")
    private Long componentId;
    @SerializedName("name")
    private String componentName;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;

    @SerializedName("layout")
    private String layout;
    @SerializedName("layout_type")
    private String layoutType;

    public Long getVisionId() {
        return visionId;
    }

    public void setVisionId(Long visionId) {
        this.visionId = visionId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
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

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }
}

package com.tnd.pw.strategy.common.requests;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StrategyRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("model_name")
    private String modelName;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("model_type")
    private String modelType;
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
    @SerializedName("layout_parent")
    private Long layoutParent;

    public Long getVisionId() {
        return visionId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
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

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public Long getLayoutParent() {
        return layoutParent;
    }

    public void setLayoutParent(Long layoutParent) {
        this.layoutParent = layoutParent;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}

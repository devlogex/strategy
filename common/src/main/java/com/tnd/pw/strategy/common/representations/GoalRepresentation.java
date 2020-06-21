package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GoalRepresentation implements Serializable {
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
    @SerializedName("parent_goal")
    private String parentGoal;
    @SerializedName("status")
    private String status;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("color")
    private String color;
    @SerializedName("metric")
    private String metric;
    @SerializedName("metric_description")
    private String metricDescription;
    @SerializedName("metric_file")
    private String metricFile;

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

    public String getParentGoal() {
        return parentGoal;
    }

    public void setParentGoal(String parentGoal) {
        this.parentGoal = parentGoal;
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

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getMetricDescription() {
        return metricDescription;
    }

    public void setMetricDescription(String metricDescription) {
        this.metricDescription = metricDescription;
    }

    public String getMetricFile() {
        return metricFile;
    }

    public void setMetricFile(String metricFile) {
        this.metricFile = metricFile;
    }
}

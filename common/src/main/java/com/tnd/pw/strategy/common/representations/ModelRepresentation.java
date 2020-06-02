package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("type")
    private String type;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("files")
    private String files;
    @SerializedName("description")
    private String description;

    public ModelRepresentation() {
    }

    public ModelRepresentation(Long id, String name, Long workspaceId, Integer type, String timeFrame, String files, String description) {
        this.id = id;
        this.name = name;
        this.workspaceId = workspaceId;
        this.type = type;
        this.timeFrame = timeFrame;
        this.files = files;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

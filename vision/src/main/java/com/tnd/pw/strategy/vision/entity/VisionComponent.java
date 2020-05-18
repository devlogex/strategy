package com.tnd.pw.strategy.vision.entity;

import com.google.gson.annotations.SerializedName;

public class VisionComponent extends BaseEntity {

    @SerializedName("name")
    private String name;
    @SerializedName("vision_id")
    private Long visionId;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVisionId() {
        return visionId;
    }

    public void setVisionId(Long visionId) {
        this.visionId = visionId;
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

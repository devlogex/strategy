package com.tnd.pw.strategy.model.entity;

import com.google.gson.annotations.SerializedName;

public class ModelComponent extends BaseEntity {
    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("color")
    private String color;

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

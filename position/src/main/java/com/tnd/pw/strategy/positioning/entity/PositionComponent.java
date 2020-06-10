package com.tnd.pw.strategy.positioning.entity;

import com.google.gson.annotations.SerializedName;

public class PositionComponent extends BaseEntity {
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("color")
    private String color;

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

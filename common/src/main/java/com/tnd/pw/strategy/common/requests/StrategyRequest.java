package com.tnd.pw.strategy.common.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class StrategyRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("start_at")
    private Long startAt;
    @SerializedName("end_at")
    private Long endAt;
    @SerializedName("visiable")
    private Integer visiable;
    @SerializedName("initiative_id")
    private Long initiativeId;
    @SerializedName("parent_initiative")
    private String parentInitiative;
    @SerializedName("goal_id")
    private Long goalId;
    @SerializedName("parent_goal")
    private String parentGoal;
    @SerializedName("status")
    private String status;
    @SerializedName("metric")
    private String metric;
    @SerializedName("metric_description")
    private String metricDescription;
    @SerializedName("metric_file")
    private String metricFile;
    @SerializedName("competitor_id")
    private Long competitorId;
    @SerializedName("competitor_name")
    private String competitorName;
    @SerializedName("url")
    private String url;
    @SerializedName("score")
    private Object score;
    @SerializedName("personas_id")
    private Long personasId;
    @SerializedName("personas_name")
    private String personasName;
    @SerializedName("content")
    private Object content;
    @SerializedName("image")
    private String image;
    @SerializedName("position_id")
    private Long positionId;
    @SerializedName("position_name")
    private String positionName;
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
    private String name;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;
    @SerializedName("buz_type")
    private String buzType;

    @SerializedName("layout")
    private String layout;
    @SerializedName("layout_type")
    private String layoutType;
    @SerializedName("layout_parent")
    private Long layoutParent;
}

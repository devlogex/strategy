package com.tnd.pw.strategy.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.UserTokenRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class StrategyRequest extends UserTokenRequest {

    @SerializedName("start_at")
    private Long startAt;
    @SerializedName("end_at")
    private Long endAt;
    @SerializedName("visiable")
    private Integer visiable;
    @SerializedName("parent_initiative")
    private String parentInitiative;
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
    @SerializedName("url")
    private String url;
    @SerializedName("score")
    private Object score;
    @SerializedName("content")
    private Object content;
    @SerializedName("image")
    private String image;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("model_type")
    private String modelType;
    @SerializedName("files")
    private String files;

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
}

package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoalRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
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
}

package com.tnd.pw.strategy.initiative.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Initiative implements Serializable {
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
    @SerializedName("parent_initiative")
    private String parentInitiative;
    @SerializedName("status")
    private Integer status;
    @SerializedName("time_frame")
    private String timeFrame;
    @SerializedName("color")
    private String color;
    @SerializedName("start_at")
    private Long startAt;
    @SerializedName("end_at")
    private Long endAt;
    @SerializedName("visible")
    private Integer visible;
}

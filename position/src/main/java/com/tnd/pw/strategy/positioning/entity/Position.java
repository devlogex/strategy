package com.tnd.pw.strategy.positioning.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("files")
    private String files;
    @SerializedName("description")
    private String description;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("buz_type")
    private String buzType;
    @SerializedName("time_frame")
    private String timeFrame;

}

package com.tnd.pw.strategy.vision.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VisionComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("files")
    private String files;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("vision_id")
    private Long visionId;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;

}

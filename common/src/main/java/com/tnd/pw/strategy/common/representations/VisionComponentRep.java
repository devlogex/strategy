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
public class VisionComponentRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("vision_id")
    private Long visionId;
    @SerializedName("name")
    private String name;
    @SerializedName("summary")
    private String summary;
    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;

}

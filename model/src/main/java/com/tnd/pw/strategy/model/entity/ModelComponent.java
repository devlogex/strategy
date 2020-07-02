package com.tnd.pw.strategy.model.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ModelComponent implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("files")
    private String files;
    @SerializedName("description")
    private String description;
    @SerializedName("model_id")
    private Long modelId;
    @SerializedName("color")
    private String color;
}

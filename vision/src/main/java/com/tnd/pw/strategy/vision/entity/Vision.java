package com.tnd.pw.strategy.vision.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Vision implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("files")
    private String files;
    @SerializedName("description")
    private String description;
    @SerializedName("product_id")
    private Long productId;

}

package com.tnd.pw.strategy.competitor.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Competitor implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("product_id")
    private Long productId;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("color")
    private String color;
    @SerializedName("score")
    private String score;
    @SerializedName("url")
    private String url;
    @SerializedName("content")
    private String content;
}

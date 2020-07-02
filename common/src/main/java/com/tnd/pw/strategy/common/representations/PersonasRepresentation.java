package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonasRepresentation implements Serializable {
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
    @SerializedName("content")
    private HashMap<String, HashMap<String, String>> content;
}

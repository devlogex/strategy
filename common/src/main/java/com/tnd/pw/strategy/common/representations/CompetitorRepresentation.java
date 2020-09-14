package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorRepresentation implements Serializable {
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
    private HashMap<String, Integer> score;
    @SerializedName("url")
    private String url;
    @SerializedName("content")
    private HashMap<String, HashMap<String, String>> content;
    @SerializedName("list_todo")
    private List<TodoRepresentation> todoReps;
    @SerializedName("list_comment")
    private List<CommentRepresentation> commentReps;
}

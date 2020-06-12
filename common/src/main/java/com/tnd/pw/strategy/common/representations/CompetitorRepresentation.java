package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class CompetitorRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("workspace_id")
    private Long workspaceId;
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

    public CompetitorRepresentation() {
    }

    public CompetitorRepresentation(Long id, Long workspaceId, String name, String image, String color, HashMap<String, Integer> score, String url, HashMap<String, HashMap<String, String>> content) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.name = name;
        this.image = image;
        this.color = color;
        this.score = score;
        this.url = url;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HashMap<String, Integer> getScore() {
        return score;
    }

    public void setScore(HashMap<String, Integer> score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, HashMap<String, String>> getContent() {
        return content;
    }

    public void setContent(HashMap<String, HashMap<String, String>> content) {
        this.content = content;
    }
}

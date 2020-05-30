package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LayoutResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("layout")
    private Object layout;

    public LayoutResponse(Object layout) {
        this.layout = layout;
    }

    public Object getLayout() {
        return layout;
    }

    public void setLayout(Object layout) {
        this.layout = layout;
    }
}

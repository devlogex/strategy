package com.tnd.pw.strategy.common.representations;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LayoutRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("layout")
    private Object layout;

    public LayoutRepresentation() {
    }

    public LayoutRepresentation(Object layout) {
        this.layout = layout;
    }

    public Object getLayout() {
        return layout;
    }

    public void setLayout(Object layout) {
        this.layout = layout;
    }
}

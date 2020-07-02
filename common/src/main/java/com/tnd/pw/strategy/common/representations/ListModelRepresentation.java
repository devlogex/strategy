package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ListModelRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_model")
    private ArrayList<ModelRepresentation> listModel;
    @SerializedName("list_component")
    private Object listComponent;

    public ArrayList<ModelRepresentation> getListModel() {
        if(listModel == null) {
            listModel = new ArrayList<>();
        }
        return listModel;
    }

    public void setListModel(ArrayList<ModelRepresentation> listModel) {
        this.listModel = listModel;
    }

    public Object getListComponent() {
        if(listComponent == null) {
            listComponent = new ArrayList<>();
        }
        return listComponent;
    }

    public void setListComponent(Object listComponent) {
        this.listComponent = listComponent;
    }
}

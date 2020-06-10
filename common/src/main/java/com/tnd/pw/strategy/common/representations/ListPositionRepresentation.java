package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ListPositionRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_position")
    private ArrayList<PositionRepresentation> listPosition;
    @SerializedName("list_component")
    private Object listComponent;

    public ListPositionRepresentation() {
    }

    public ListPositionRepresentation(ArrayList<PositionRepresentation> listPosition, Object listComponent) {
        this.listPosition = listPosition;
        this.listComponent = listComponent;
    }

    public ArrayList<PositionRepresentation> getListPosition() {
        if(listPosition == null) {
            listPosition = new ArrayList<>();
        }
        return listPosition;
    }

    public void setListPosition(ArrayList<PositionRepresentation> listPosition) {
        this.listPosition = listPosition;
    }

    public Object getListComponent() {
        return listComponent;
    }

    public void setListComponent(Object listComponent) {
        this.listComponent = listComponent;
    }
}

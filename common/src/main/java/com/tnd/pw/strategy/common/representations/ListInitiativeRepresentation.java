package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ListInitiativeRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_initiative")
    private ArrayList<InitiativeRepresentation> initiatives;
    @SerializedName("list_initiative_status")
    private HashMap<String, ArrayList<InitiativeRepresentation>> initiativeStatus;

    public ArrayList<InitiativeRepresentation> getInitiatives() {
        if(initiatives == null) {
            initiatives = new ArrayList<>();
        }
        return initiatives;
    }

    public void setInitiatives(ArrayList<InitiativeRepresentation> initiatives) {
        this.initiatives = initiatives;
    }

    public HashMap<String, ArrayList<InitiativeRepresentation>> getInitiativeStatus() {
        if(initiativeStatus == null) {
            initiativeStatus = new HashMap<>();
        }
        return initiativeStatus;
    }

    public void setInitiativeStatus(HashMap<String, ArrayList<InitiativeRepresentation>> initiativeStatus) {
        this.initiativeStatus = initiativeStatus;
    }
}

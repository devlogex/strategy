package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ListInitiativeRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_initiative")
    private ArrayList<InitiativeRep> initiatives;
    @SerializedName("list_initiative_status")
    private HashMap<String, ArrayList<InitiativeRep>> initiativeStatus;

    public ArrayList<InitiativeRep> getInitiatives() {
        if(initiatives == null) {
            initiatives = new ArrayList<>();
        }
        return initiatives;
    }

    public void setInitiatives(ArrayList<InitiativeRep> initiatives) {
        this.initiatives = initiatives;
    }

    public HashMap<String, ArrayList<InitiativeRep>> getInitiativeStatus() {
        if(initiativeStatus == null) {
            initiativeStatus = new HashMap<>();
        }
        return initiativeStatus;
    }

    public void setInitiativeStatus(HashMap<String, ArrayList<InitiativeRep>> initiativeStatus) {
        this.initiativeStatus = initiativeStatus;
    }
}

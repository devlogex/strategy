package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.development.common.representations.ReleaseRep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsStrategyRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("initiatives")
    private List<InitiativeRep> initiativeReps;
    @SerializedName("releases")
    private List<ReleaseRep> releaseReps;
}

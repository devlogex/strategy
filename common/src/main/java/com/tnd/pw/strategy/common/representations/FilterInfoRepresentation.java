package com.tnd.pw.strategy.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FilterInfoRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("time_frame")
    private List<String> timeFrames;
    @SerializedName("buz_type")
    private List<String> buzTypes;
}

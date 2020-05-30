package com.tnd.pw.strategy.common.representations;


import java.io.Serializable;
import java.util.ArrayList;

public class LayoutRepresentation<T> extends ArrayList<ArrayList<ArrayList<T>>> implements Serializable {
    private static final long serialVersionUID = 1L;

    public int getSize() {
        int size = 0;
        for(int i = 0; i < this.size(); i++) {
            for(int j = 0; j < this.get(i).size(); j++) {
                size += this.get(i).get(j).size();
            }
        }
        return size;
    }
}

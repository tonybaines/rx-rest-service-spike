package com.bt.uta.webservices.async.dto;

import java.util.Map;

public class DataLayerRequest {


    public enum Fields {name;}
    public final String name;

    public static DataLayerRequest from(Map<String, String[]> parameterMap) {
        if (parameterMap.containsKey(Fields.name.name())) {
            return new DataLayerRequest(parameterMap.get(Fields.name.name())[0]);
        }
        else {
            throw new IllegalArgumentException("No name supplied");
        }
    }

    private DataLayerRequest(String name) {this.name = name;}
}

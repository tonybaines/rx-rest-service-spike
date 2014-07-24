package com.bt.uta.webservices.async.services;

import java.util.HashMap;
import java.util.Map;

import com.bt.uta.webservices.async.dto.DataLayerRequest;
import com.bt.uta.webservices.async.dto.DataLayerResponse;

/**
 * Simple stub repository for simulating a DB lookup
 */
public class MessageRepository {
    private final Map<String, String> namesToMessages = new HashMap<String, String>() {
        {
            put("tony", "Hello");
            put("pierre", "Bonjour");
            put("fab", "Ciao");
        }
    };

    public DataLayerResponse findMessageFor(DataLayerRequest request) {
        if(namesToMessages.containsKey(request.name)) {
            try {Thread.sleep(1000);} catch (InterruptedException ignored) {}; // fake some latency

            return new DataLayerResponse(namesToMessages.get(request.name) + " " + request.name);
        }else {
            throw new IllegalArgumentException("Unknown person "+request.name);
        }
    }
}

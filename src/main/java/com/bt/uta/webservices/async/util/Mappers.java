package com.bt.uta.webservices.async.util;

import com.bt.uta.webservices.async.dto.DataLayerRequest;
import com.bt.uta.webservices.async.dto.DataLayerResponse;
import com.bt.uta.webservices.async.services.MessageRepository;
import javax.servlet.http.HttpServletRequest;
import rx.functions.Func1;

/**
 * Factory methods for the 'map' functions which transform the output from one step into something new
 */
public class Mappers {

    public static Func1<HttpServletRequest, DataLayerRequest> newHttpRequestToDataLayerRequestMapper() {
        return new Func1<HttpServletRequest, DataLayerRequest>() {
            public DataLayerRequest call(HttpServletRequest httpServletRequest) {
                return DataLayerRequest.from(httpServletRequest.getParameterMap());
            }
        };
    }

    public static Func1<DataLayerRequest, DataLayerResponse> newDataLayerRequestToDataLayerResponseMapper(final MessageRepository messageRepository) {
        return new Func1<DataLayerRequest, DataLayerResponse>() {
            public DataLayerResponse call(DataLayerRequest dataLayerRequest) {
                return messageRepository.findMessageFor(dataLayerRequest);
            }
        };
    }

    public static Func1<DataLayerResponse, String> newDataLayerResponseToResultMapper() {
        return new Func1<DataLayerResponse, String>() {
            public String call(DataLayerResponse dataLayerResponse) {
                return dataLayerResponse.message;
            }
        };
    }
}

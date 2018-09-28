package com.madbid.core.service.proxy;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by nikolov.
 */
@JsonSerialize(using = ResponseDTOJsonSerializer.class)
@JsonDeserialize(using = ResponseDTOJsonDeserializer.class)
public class ResponseDTO implements Serializable {
    private boolean subscribe;

    public boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }
}

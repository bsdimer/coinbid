package com.madbid.core.service.proxy;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by alexn on 26.12.2014 г..
 */
@JsonSerialize(using = MessageCriteriaDTOJsonSerializer.class)
@JsonDeserialize(using = MessageCriteriaDTOJsonDeserializer.class)
public class MessageCriteriaDTO implements Serializable {
    private Integer page;
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

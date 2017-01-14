package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kolao_000 on 2017-01-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsertEntriesResponse extends BaseResponse {

    /**
     * Gets or sets the entity identifier of updated entities.
     */
    @JsonProperty("ids")
    public List<String> ids;
}

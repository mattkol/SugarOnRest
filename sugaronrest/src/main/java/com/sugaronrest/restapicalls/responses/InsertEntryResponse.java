package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kolao_000 on 2017-01-09.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsertEntryResponse extends BaseResponse {
    /**
     * Gets or sets the entity identifier of inserted entity
     */
    @JsonProperty("id")
    public String id;
}

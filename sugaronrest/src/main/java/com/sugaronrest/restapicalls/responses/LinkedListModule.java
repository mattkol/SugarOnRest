package com.sugaronrest.restapicalls.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by kolao_000 on 2017-01-09.
 */
public class LinkedListModule {

    @JsonProperty("link_list")
    public List<LinkedListModuleData> moduleDataList;
}

package com.sugaronrest.tests.custommodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sugaronrest.modules.*;

import java.util.List;

/**
 * Created by kolao_000 on 2017-01-08.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAcccount3  extends Accounts {

    public List<Bugs> getLeadLink() {
        return bugLink;
    }

    public void setLeadLink(List<Bugs> value) {
        bugLink = value;
    }

    public List<Cases> getCaseLink() {
        return caseLink;
    }

    public void setCaseLink(List<Cases> value) {
        caseLink = value;
    }

    @JsonProperty("bugs")
    private List<Bugs> bugLink;

    @JsonProperty("cases")
    private List<Cases> caseLink;
}
package com.sugaronrest.tests.custommodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sugaronrest.modules.*;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAcccount3  extends Accounts {

    public List<Leads> getLeadLink() {
        return leadLink;
    }

    public void setLeadLink(List<Leads> value) {
        leadLink = value;
    }

    public List<Cases> getCaseLink() {
        return caseLink;
    }

    public void setCaseLink(List<Cases> value) {
        caseLink = value;
    }

    @JsonProperty("leads")
    private List<Leads> leadLink;

    @JsonProperty("cases")
    private List<Cases> caseLink;
}
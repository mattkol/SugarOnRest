package com.sugaronrest.tests.custommodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Cases;
import com.sugaronrest.modules.Contacts;
import com.sugaronrest.modules.Leads;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAcccount2  extends Accounts {

    public List<Contacts> getContactLink() {
        return contactLink;
    }

    public void setContactLink(List<Contacts> value) {
        contactLink = value;
    }

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


    @JsonProperty("contacts")
    private List<Contacts> contactLink;

    @JsonProperty("leads")
    private List<Leads> leadLink;

    @JsonProperty("cases")
    private List<Cases> caseLink;
}
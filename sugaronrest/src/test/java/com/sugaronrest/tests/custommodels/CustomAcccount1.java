package com.sugaronrest.tests.custommodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Contacts;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAcccount1  extends Accounts {

    public List<Contacts> getContactLink() {
        return contactLink;
    }

    public void setContactLink(List<Contacts> value) {
        contactLink = value;
    }

    @JsonProperty("contacts")
    private List<Contacts> contactLink;
}


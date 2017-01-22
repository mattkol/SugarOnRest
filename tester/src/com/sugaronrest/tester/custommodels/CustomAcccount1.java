package com.sugaronrest.tester.custommodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Contacts;

import java.util.List;

/**
 * Created by kolao_000 on 2017-01-08.
 */
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


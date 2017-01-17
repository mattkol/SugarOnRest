package com.sugaronrest.tests.helpers;

import com.sugaronrest.*;
import com.sugaronrest.modules.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LinkedModules {

    public static SugarRestResponse readAccountLinkContact(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest(RequestType.LinkedReadById);
        request.setModuleName("Accounts");
        request.setParameter(accountId);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();

        List<String> selectContactFields = new ArrayList<String>();
        selectContactFields.add(NameOf.Contacts.FirstName);
        selectContactFields.add(NameOf.Contacts.LastName);
        selectContactFields.add(NameOf.Contacts.Title);
        selectContactFields.add(NameOf.Contacts.Description);
        selectContactFields.add(NameOf.Contacts.PrimaryAddressPostalcode);

        linkedListInfo.put(Contacts.class, selectContactFields);
        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }

    public static SugarRestResponse readAccountLinkItems(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.LinkedReadById);
        request.setParameter(accountId);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();
        linkedListInfo.put(Contacts.class, null);
        linkedListInfo.put("Leads", null);
        linkedListInfo.put(Cases.class, null);

        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }

    public static SugarRestResponse readAccountLinkItems2(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.LinkedBulkRead);
        request.getOptions().setMaxResult(count);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();
        linkedListInfo.put(Contacts.class, null);
        linkedListInfo.put("Leads", null);
        linkedListInfo.put(Cases.class, null);

        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }

    public static SugarRestResponse bulkReadAccountLinkContact(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest(RequestType.LinkedBulkRead);
        request.setModuleName("Accounts");
        request.getOptions().setMaxResult(count);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();

        List<String> selectContactFields = new ArrayList<String>();
        selectContactFields.add(NameOf.Contacts.FirstName);
        selectContactFields.add(NameOf.Contacts.LastName);
        selectContactFields.add(NameOf.Contacts.Title);
        selectContactFields.add(NameOf.Contacts.Description);
        selectContactFields.add(NameOf.Contacts.PrimaryAddressPostalcode);

        linkedListInfo.put(Contacts.class, selectContactFields);
        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }

    public static SugarRestResponse bulkReadAccountLinkItems(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest(RequestType.LinkedBulkRead);
        request.setModuleType(Accounts.class);
        request.getOptions().setMaxResult(count);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();
        linkedListInfo.put("Contacts", null);
        linkedListInfo.put(Leads.class, null);
        linkedListInfo.put(Cases.class, null);

        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }

    public static SugarRestResponse bulkReadAccountLinkItems2(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.LinkedBulkRead);
        request.getOptions().setMaxResult(count);

        List<String> selectedFields = new ArrayList<String>();


        selectedFields.add("id");
        selectedFields.add("name");
        selectedFields.add("industry");
        selectedFields.add("website");

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();

        List<String> selectContactFields = new ArrayList<String>();
        selectContactFields.add(NameOf.Contacts.FirstName);
        selectContactFields.add(NameOf.Contacts.LastName);
        selectContactFields.add(NameOf.Contacts.Title);
        selectContactFields.add(NameOf.Contacts.Description);
        selectContactFields.add(NameOf.Contacts.PrimaryAddressPostalcode);

        linkedListInfo.put(Leads.class, selectContactFields);

        // Get all fields for Bug
        linkedListInfo.put("Cases", null);
        linkedListInfo.put("Contacts", selectContactFields);

        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }
}
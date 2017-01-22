package com.sugaronrest.tester;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Contacts;
import com.sugaronrest.tester.custommodels.CustomAcccount1;
import com.sugaronrest.tester.custommodels.CustomAcccount2;
import com.sugaronrest.tester.helpers.AccountsModule;
import com.sugaronrest.tester.helpers.LinkedModules;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kolao_000 on 2017-01-08.
 */
public class LinkedModulesTests {

    public static void linkedRead1Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Bulk Read Account-------------------
        int count = 10;
        SugarRestResponse response = AccountsModule.bulkReadAccount(client, count);
        assertNotNull(response);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> readAccounts = (List<Accounts>)(response.getData());
        assertNotNull(readAccounts);
        assertTrue(readAccounts.size() <= count);

        // -------------------End Bulk Read Account-------------------
        // -------------------Read Account Link Contact-------------------
        String accountId = readAccounts.get(count - 1).getId();
        accountId ="28270ac1-2a6b-e674-b751-5777b5c57439";
        response = LinkedModules.readAccountLinkContact(client, accountId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNull(response.getData());
        assertNotNull(jsonData);

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        CustomAcccount1 customAccount = mapper.readValue(jsonData, CustomAcccount1.class);
        assertNotNull(customAccount);
        assertEquals(accountId, customAccount.getId());
    }

    public static void linkedRead2Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Bulk Read Account-------------------
        int count = 10;
        SugarRestResponse response = AccountsModule.bulkReadAccount(client, count);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> readAccounts = (List<Accounts>)response.getData();
        assertNotNull(readAccounts);
        assertTrue(readAccounts.size() <= count);
        // -------------------End Bulk Read Account-------------------


        // -------------------Read Account Link Concat-------------------
        String accountId = readAccounts.get(count - 1).getId();
        response = LinkedModules.readAccountLinkItems(client, accountId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNotNull(jsonData);
        assertNotSame(jsonData, StringUtils.EMPTY );

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        CustomAcccount2 customAccount = mapper.readValue(jsonData, CustomAcccount2.class);

        assertNotNull(customAccount);
        assertEquals(accountId, customAccount.getId());

        // -------------------End Read Account Link Concat-------------------
    }

    public static void bulkLinkedRead1Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Read Account Link Contact-------------------
        int count = 10;
        SugarRestResponse response = LinkedModules.bulkReadAccountLinkContact(client, count);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNull(response.getData());
        assertNotNull(jsonData);

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CustomAcccount1.class) ;
        List<CustomAcccount1> customAccounts = mapper.readValue(jsonData, type);
        assertNotNull(customAccounts);
        assertEquals(customAccounts.size(), count);
    }

    public static SugarRestResponse bulkReadAccountLinkItems2(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.LinkedBulkRead);
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

        linkedListInfo.put(Contacts.class, selectContactFields);

        // Get all fields for Bug
        linkedListInfo.put("Bugs", null);

        request.getOptions().setLinkedModules(linkedListInfo);

        return client.execute(request);
    }
}


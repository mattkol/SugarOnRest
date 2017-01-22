package com.sugaronrest.tests;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Contacts;
import com.sugaronrest.tests.custommodels.CustomAcccount1;
import com.sugaronrest.tests.custommodels.CustomAcccount2;
import com.sugaronrest.tests.custommodels.CustomAcccount3;
import com.sugaronrest.tests.helpers.AccountsModule;
import com.sugaronrest.tests.helpers.LinkedModules;
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


public class LinkedModulesTests {

    @Test
    public void linkedRead1Test() throws IOException {
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

    @Test
    public void linkedRead2Test() throws IOException {
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

    @Test
    public void linkedRead3Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Bulk Read Account-------------------
        int count = 10;

        // -------------------Read Account Link Concat-------------------
        SugarRestResponse response = LinkedModules.readAccountLinkItems2(client, count);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNotNull(jsonData);
        assertNotSame(jsonData, StringUtils.EMPTY );


        // -------------------End Read Account Link Concat-------------------
    }

    @Test
    public void bulkLinkedRead1Test() throws IOException {
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

    @Test
    public void bulkLinkedRead2Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Read Account Link Items-------------------
        int count = 10;
        SugarRestResponse response = LinkedModules.bulkReadAccountLinkItems(client, count);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNull(response.getData());
        assertNotNull(jsonData);

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CustomAcccount2.class) ;
        List<CustomAcccount2> customAccounts = mapper.readValue(jsonData, type);
        assertNotNull(customAccounts);
        assertEquals(customAccounts.size(), count);

        // -------------------End Account Link Items-------------------
    }

    @Test
    public void bulkLinkedRead3Test() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        // -------------------Read Account Link Items-------------------
        int count = 10;
        SugarRestResponse response = LinkedModules.bulkReadAccountLinkItems2(client, count);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String jsonData = response.getJData();
        assertNull(response.getData());
        assertNotNull(jsonData);

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CustomAcccount3.class) ;
        List<CustomAcccount3> customAccounts = mapper.readValue(jsonData, type);
        assertNotNull(customAccounts);
        assertEquals(customAccounts.size(), count);
        // -------------------End Account Link Items-------------------
    }
}


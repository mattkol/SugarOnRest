package com.sugaronrest.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.tests.helpers.AccountsModule;
import com.sugaronrest.utils.JsonObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static com.sugaronrest.QueryOperator.Contains;
import static org.junit.Assert.*;


public class AccountsModuleTests {

    @Test
    public void crudTest() throws JsonProcessingException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        Accounts insertAccount = AccountsModule.getTestAccount();

        // -------------------Create Account-------------------
        SugarRestResponse response = AccountsModule.createAccount(client, insertAccount);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String insertId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(insertId);
        assertNotSame(insertId, StringUtils.EMPTY );

        // -------------------End Create Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccount(client, insertId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnCreateAccount = (Accounts)response.getData();

        assertNotNull(readOnCreateAccount);
        assertEquals(insertAccount.getName(), readOnCreateAccount.getName());
        assertEquals(insertAccount.getIndustry(), readOnCreateAccount.getIndustry());
        assertEquals(insertAccount.getWebsite(), readOnCreateAccount.getWebsite());
        assertEquals(insertAccount.getShippingAddressCity(), readOnCreateAccount.getShippingAddressCity());
        // -------------------End Read Account-------------------


        // -------------------Update Account-------------------
        response = AccountsModule.updateAccount(client, readOnCreateAccount.getId());

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String updateId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(updateId);
        assertNotSame(updateId, StringUtils.EMPTY );
        // -------------------End Update Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccount(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnUpdateAccount = (Accounts)response.getData();

        assertNotNull(readOnUpdateAccount.getName());
        assertNotSame(readOnUpdateAccount.getName(), StringUtils.EMPTY );
        assertEquals(updateId, updateId);
        assertNotEquals(readOnCreateAccount.getName(), readOnUpdateAccount.getName());
        // -------------------End Read Account-------------------

        // -------------------Delete Account-------------------
        response = AccountsModule.deleteAccount(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String deleteId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(deleteId);
        assertNotSame(deleteId, StringUtils.EMPTY );
        assertEquals(insertId, deleteId);

        // -------------------End Delete Account-------------------

    }

    @Test
    public void crudByTypeTest() throws JsonProcessingException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        Accounts insertAccount = AccountsModule.getTestAccount();

        // -------------------Create Account-------------------
        SugarRestResponse response = AccountsModule.createAccountByType(client, insertAccount);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String insertId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(insertId);
        assertNotSame(insertId, StringUtils.EMPTY );

        // -------------------End Create Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccountByType(client, insertId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnCreateAccount = (Accounts)response.getData();

        assertNotNull(readOnCreateAccount);
        assertEquals(insertAccount.getName(), readOnCreateAccount.getName());
        assertEquals(insertAccount.getIndustry(), readOnCreateAccount.getIndustry());
        assertEquals(insertAccount.getWebsite(), readOnCreateAccount.getWebsite());
        assertEquals(insertAccount.getShippingAddressCity(), readOnCreateAccount.getShippingAddressCity());
        // -------------------End Read Account-------------------


        // -------------------Update Account-------------------
        response = AccountsModule.updateAccountByType(client, readOnCreateAccount.getId());

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String updateId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(updateId);
        assertNotSame(updateId, StringUtils.EMPTY );
        // -------------------End Update Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccount(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnUpdateAccount = (Accounts)response.getData();

        assertNotNull(readOnUpdateAccount.getName());
        assertNotSame(readOnUpdateAccount.getName(), StringUtils.EMPTY );
        assertEquals(updateId, updateId);
        assertNotEquals(readOnCreateAccount.getName(), readOnUpdateAccount.getName());
        // -------------------End Read Account-------------------

        // -------------------Delete Account-------------------
        response = AccountsModule.deleteAccountByType(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String deleteId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(deleteId);
        assertNotSame(deleteId, StringUtils.EMPTY );
        assertEquals(insertId, deleteId);

        // -------------------End Delete Account-------------------
    }

    @Test
    public void crudJDataTest() throws IOException {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);

        Accounts insertAccount = AccountsModule.getTestAccount();

        // -------------------Create Account-------------------
        SugarRestResponse response = AccountsModule.createAccount(client, insertAccount);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String insertId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(insertId);
        assertNotSame(insertId, StringUtils.EMPTY );

        // -------------------End Create Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccountByType(client, insertId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnCreateAccount = (Accounts)response.getData();

        assertNotNull(readOnCreateAccount);
        assertEquals(insertAccount.getName(), readOnCreateAccount.getName());
        assertEquals(insertAccount.getIndustry(), readOnCreateAccount.getIndustry());
        assertEquals(insertAccount.getWebsite(), readOnCreateAccount.getWebsite());
        assertEquals(insertAccount.getShippingAddressCity(), readOnCreateAccount.getShippingAddressCity());

        // Test JData
        assertNotNull(response.getJData());
        assertNotEquals(response.getJData(), StringUtils.EMPTY);

        ObjectMapper mapper = JsonObjectMapper.getMapper();
        Map<String, Object> nameValueMap =  mapper.readValue(response.getJData(), HashMap.class);

        assertNotNull(nameValueMap);

        List<String> selectedFields = AccountsModule.getJsonSelectedField();

        assertEquals(selectedFields.size(), nameValueMap.size());

        for (Map.Entry<String, Object> item: nameValueMap.entrySet()) {
            assertTrue(selectedFields.contains(item.getKey()));
        }
        // -------------------End Read Account-------------------


        // -------------------Update Account-------------------
        response = AccountsModule.updateAccountByType(client, readOnCreateAccount.getId());

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String updateId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(updateId);
        assertNotSame(updateId, StringUtils.EMPTY );
        // -------------------End Update Account-------------------


        // -------------------Read Account-------------------
        response = AccountsModule.readAccount(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts readOnUpdateAccount = (Accounts)response.getData();

        assertNotNull(readOnUpdateAccount.getName());
        assertNotSame(readOnUpdateAccount.getName(), StringUtils.EMPTY );
        assertEquals(updateId, updateId);
        assertNotEquals(readOnCreateAccount.getName(), readOnUpdateAccount.getName());
        // -------------------End Read Account-------------------

        // -------------------Delete Account-------------------
        response = AccountsModule.deleteAccountByType(client, updateId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        String deleteId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

        assertNotNull(deleteId);
        assertNotSame(deleteId, StringUtils.EMPTY );
        assertEquals(insertId, deleteId);

        // -------------------End Delete Account-------------------
    }

    @Test
    public void bulkCRUDTest() {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);
        List<Accounts> insertAccounts = AccountsModule.getTestBulkAccount();

        // -------------------Create Bulk Account-------------------
        SugarRestResponse response = AccountsModule.bulkCreateAccount(client, insertAccounts);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<String> insertIds = (response.getData() == null) ? null : (List<String>)response.getData();

        assertNotNull(insertIds);
        assertEquals(insertAccounts.size(), insertIds.size());

        for (String id : insertIds)
        {
            assertNotNull(id);
            assertNotSame(StringUtils.EMPTY, id);
        }
        // -------------------End Bulk Create Account-------------------


        // -------------------Bulk Read Account-------------------
        List<Accounts> readOnCreateAccounts = AccountsModule.bulkReadAccount2(client, insertIds);

        assertNotNull(readOnCreateAccounts);
        assertEquals(insertIds.size(), readOnCreateAccounts.size());

        for (Accounts account : readOnCreateAccounts)
        {
            assertNotNull(account);
            assertNotNull(account.getId());
            assertNotSame(StringUtils.EMPTY, account.getId());
        }

        // -------------------End Bulk Read Account-------------------


        // -------------------Bulk Update Account-------------------
        Map<String, String> accountNameMap = new HashMap<String, String>();

        for (Accounts account : readOnCreateAccounts)
        {
            accountNameMap.put(account.getId(), account.getName());
        }

        response = AccountsModule.bulkUpdateAccount(client, readOnCreateAccounts);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<String> updateIds = (response.getData() == null) ? null : (List<String>)response.getData();

        assertNotNull(updateIds);
        for (String id : updateIds)
        {
            assertNotNull(id);
            assertNotSame(StringUtils.EMPTY, id);
        }
        // -------------------End Bulk Update Account-------------------


        // -------------------Bulk Read Account-------------------
        List<Accounts> readOnUpdateAccounts = AccountsModule.bulkReadAccount2(client, updateIds);

        assertNotNull(readOnUpdateAccounts);
        assertEquals(updateIds.size(), readOnUpdateAccounts.size());

        for (Map.Entry<String, String> entry : accountNameMap.entrySet()) {
            String key = entry.getKey();
            assertTrue(updateIds.contains(key));
        }

        for (Accounts account : readOnUpdateAccounts)
        {
            assertNotNull(account);
            String value = accountNameMap.get(account.getId());
            assertNotEquals(value, account.getName());
        }
        // -------------------End Bulk Read Account-------------------

        // -------------------Bulk Delete Account-------------------
        List<String> deleteIds = AccountsModule.bulkDeleteAccount(client, updateIds);

        assertNotNull(deleteIds);
        assertEquals(updateIds.size(), deleteIds.size());

        for (String id : updateIds) {
            assertTrue(deleteIds.contains(id));
        }

        // -------------------End Bulk Delete Account-------------------
    }

    @Test
    public void readBulkAccountTest() {

        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(5);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> accounts = (List<Accounts>)response.getData();
        assertEquals(5, accounts.size());
    }

    @Test
    public void readAccountSelectFieldTest() throws IOException {

        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(1);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> accounts = (List<Accounts>)response.getData();
        assertEquals(1, accounts.size());

        // Read by Id
        request.setRequestType(RequestType.ReadById);
        request.setParameter(accounts.get(0).getId());

        List<String> selectedFields = new ArrayList<String>();
        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.DateEntered);

        request.getOptions().setSelectFields(selectedFields);

        response = client.execute(request);
        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        Accounts account = (Accounts)response.getData();
        assertNotNull(account);

        // Check select fields
        String jsonData = response.getJData();
        assertNotNull(jsonData);
        assertNotEquals(jsonData, StringUtils.EMPTY);

        ObjectMapper mapper = JsonObjectMapper.getMapper();
        Map<String, Object> nameValueMap =  mapper.readValue(jsonData, HashMap.class);

        assertNotNull(nameValueMap);
        assertEquals(selectedFields.size(), nameValueMap.size());

        for (Map.Entry<String, Object> entry : nameValueMap.entrySet()) {
            String key = entry.getKey();
            assertTrue(selectedFields.contains(key));
        }
    }

    @Test
    public void readPagedAccountTest() {

        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.PagedRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setCurrentPage(1);
        request.getOptions().setNumberPerPage(10);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> accounts = (List<Accounts>)response.getData();
        assertEquals(10, accounts.size());
    }

    @Test
    public void deleteGroupAccountTest() {

        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        // Since query is set, the query predicates will be ignored.
        queryPredicates.add(new QueryPredicate("name", Contains, "SugarOnRest"));
        request.getOptions().setQueryPredicates(queryPredicates);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> accounts = (List<Accounts>)response.getData();
        if (accounts != null){
            for (Accounts account : accounts) {

                // -------------------Delete Account-------------------
                response = AccountsModule.deleteAccount(client, account.getId());

                assertNotNull(response);
                assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

                String deleteId = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();

                assertNotNull(deleteId);
                assertNotSame(deleteId, StringUtils.EMPTY );
                assertEquals(account.getId(), deleteId);

                // -------------------End Delete Account-------------------
            }
        }
    }
}

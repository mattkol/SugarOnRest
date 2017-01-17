package com.sugaronrest.tests.helpers;

import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountsModule {

    public static SugarRestResponse createAccount(SugarRestClient client, Accounts account) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.Create);
        request.setParameter(account);

        request.getOptions().setSelectFields(getSelectedField());

        return client.execute(request);
    }

    public static SugarRestResponse createAccountByType(SugarRestClient client, Accounts account)
    {
        SugarRestRequest request = new SugarRestRequest(RequestType.Create);
        request.setParameter(account);
        request.setModuleType(Accounts.class);

        request.getOptions().setSelectFields(getSelectedField());

        return client.execute(request);
    }

    public static SugarRestResponse readAccount(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.ReadById);
        request.setParameter(accountId);

        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Id);

        return client.execute(request);
    }

    public static SugarRestResponse readAccountByType(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.ReadById);
        request.setParameter(accountId);

        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Id);

        return client.execute(request);
    }

    public static SugarRestResponse updateAccount(SugarRestClient client, String identifier) {
        Random random = new Random();
        int uniqueNumber = 10000 + random.nextInt(1000);

        Accounts account = new Accounts();
        account.setId(identifier);
        account.setName("Update SugarOnRest Acccount " + uniqueNumber);

        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.Update);
        request.setParameter(account);

        request.getOptions().setSelectFields(new ArrayList<String>());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Name);

        return client.execute(request);
    }

    public static SugarRestResponse updateAccountByType(SugarRestClient client, String identifier) {
        Random random = new Random();
        int uniqueNumber = 10000 + random.nextInt(1000);

        Accounts account = new Accounts();
        account.setId(identifier);
        account.setName("Update SugarOnRest Account " + uniqueNumber);

        SugarRestRequest request = new SugarRestRequest(RequestType.Update);
        request.setModuleType(Accounts.class);
        request.setParameter(account);

        request.getOptions().setSelectFields(new ArrayList<String>());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Name);

        return client.execute(request);
    }

    public static SugarRestResponse deleteAccount(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.Delete);
        request.setParameter(accountId);

        return client.execute(request);
    }

    public static SugarRestResponse deleteAccountByType(SugarRestClient client, String accountId) {
        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.Delete);
        request.setParameter(accountId);

        return client.execute(request);
    }

    public static SugarRestResponse bulkReadAccount(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.BulkRead);

        List<String> selectedFields = getSelectedField();
        selectedFields.add(NameOf.Accounts.Id);
        request.getOptions().setSelectFields(selectedFields);
        request.getOptions().setMaxResult(count);

        return client.execute(request);
    }

    public static List<Accounts> bulkReadAccount2(SugarRestClient client, List<String> accountIds) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.ReadById);

        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Id);

        List<Accounts> accounts = new ArrayList<Accounts>();

        for (String id : accountIds) {
            request.setParameter(id);
            SugarRestResponse response = client.execute(request);

            accounts.add((Accounts)response.getData());
        }

        return accounts;
    }

    public static SugarRestResponse bulkCreateAccount(SugarRestClient client, List<Accounts> accounts) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.BulkCreate);
        request.setParameter(accounts);
        request.getOptions().setSelectFields(getSelectedField());
        return client.execute(request);
    }

    public static SugarRestResponse bulkUpdateAccount(SugarRestClient client, List<Accounts> accounts) {
        for (Accounts account : accounts)
        {
            String name = account.getName().replace("New", "Update");
            account.setName(name);
        }

        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.BulkUpdate);
        request.setParameter(accounts);

        request.getOptions().setSelectFields(new ArrayList<String>());
        request.getOptions().getSelectFields().add(NameOf.Accounts.Name);

        return client.execute(request);
    }

    public static List<String> bulkDeleteAccount(SugarRestClient client, List<String> accountIds) {
        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.Delete);

        List<String> listId = new ArrayList<String>();
        for (String id : accountIds)
        {
            request.setParameter(id);
            SugarRestResponse response = client.execute(request);
            String identifier = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();
            listId.add(identifier);
        }

        return listId;
    }

    public static List<Accounts> getTestBulkAccount() {
        Random random = new Random();
        int uniqueNumber = 10000 + random.nextInt(1000);

        List<Accounts> accounts = new ArrayList<Accounts>();

        Accounts account = new Accounts();
        account.setName("New SugarOnRest Acccount 1 " + uniqueNumber);
        account.setIndustry("Manufacturing");
        account.setWebsite("www.sugaronrest1.com");
        account.setShippingAddressCity("Los Angeles");
        accounts.add(account);

        account = new Accounts();
        account.setName("New SugarOnRest Acccount 2 " + uniqueNumber);
        account.setIndustry("Fishing");
        account.setWebsite("www.sugaronrest2.com");
        account.setShippingAddressCity("New York");
        accounts.add(account);

        account = new Accounts();
        account.setName("New SugarOnRest Acccount 3 " + uniqueNumber);
        account.setIndustry("Finance");
        account.setWebsite("www.sugaronrest3.com");
        account.setShippingAddressCity("New Orlean");
        accounts.add(account);

        account = new Accounts();
        account.setName("New SugarOnRest Acccount 4 " + uniqueNumber);
        account.setIndustry("Computing");
        account.setWebsite("www.sugaronrest4.com");
        account.setShippingAddressCity("New Orlean");
        accounts.add(account);

        account = new Accounts();
        account.setName("New SugarOnRest Acccount 5 " + uniqueNumber);
        account.setIndustry("Shipping");
        account.setWebsite("www.sugaronrest5.com");
        account.setShippingAddressCity("New Orlean");
        accounts.add(account);

        return accounts;
    }

    public static List<String> getSelectedField() {
        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        return selectedFields;
    }

    public static List<String> getJsonSelectedField() {
        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add("id");
        selectedFields.add("name");
        selectedFields.add("industry");
        selectedFields.add("website");
        selectedFields.add("shipping_address_city");

        return selectedFields;
    }

    public static Accounts getTestAccount() {
        Random random = new Random();
        int uniqueNumber = 10000 + random.nextInt(1000);

        Accounts account = new Accounts();
        account.setName("New SugarOnRest Acccount " + uniqueNumber);
        account.setIndustry("Manufacturing");
        account.setWebsite("www.sugaronrest.com");
        account.setShippingAddressCity("Los Angeles");

        return account;
    }
}

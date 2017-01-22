package com.sugaronrest.tests;

import com.sugaronrest.*;
import com.sugaronrest.modules.Accounts;
import com.sugaronrest.modules.Cases;
import com.sugaronrest.modules.Leads;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.sugaronrest.QueryOperator.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class QueryTests {

    @Test
    public void readBulkWithQuery1Test() {

        SugarRestRequest request = new SugarRestRequest("Accounts", RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(10);

        request.getOptions().setQuery(" accounts.name = 'Air Safety Inc' ");
        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        // Since query is set, the query predicates will be ignored.
        queryPredicates.add(new QueryPredicate("name", Equal, "General Electric USA, Inc"));
        request.getOptions().setQueryPredicates(queryPredicates);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> readAccounts = (List<Accounts>)response.getData();
        assertNotNull(readAccounts);

        // Check if all contains - "Air Safety Inc";
        if (readAccounts.size() >=  1) {
            boolean allContains = false;
            for (Accounts account : readAccounts) {
                allContains = account.getName().toLowerCase().contains("Air Safety Inc".toLowerCase());

                if (!allContains) {
                    break;
                }
            }

            assertTrue(allContains);
        }
    }

    @Test
    public void readBulkWithQuery2Test() {

        SugarRestRequest request = new SugarRestRequest("Leads", RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(10);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Leads> readLeads = (List<Leads>)response.getData();
        assertNotNull(readLeads);

        // Read back with query
        // Get all identifiers
        List<String> identifiers = new ArrayList<String>();
        for (Leads lead : readLeads) {
            identifiers.add(lead.getId());
        }

        // Reset options
        request.setOptions(new Options());
        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        // Set query in this format:
        // "leads.id IN('10d82d59-08eb-8f0d-28e0-5777b57af47c', '12037cd0-ead2-402e-e1d0-5777b5dfb965', '13d4109d-c5ca-7dd1-99f1-5777b57ef30f', '14c136e5-1a67-eeba-581c-5777b5c8c463', '14e4825e-9573-4d75-2dbe-5777b5b7ee85', '1705b33a-3fad-aa70-77ef-5777b5b081f1', '171c1d8b-e34f-3a1f-bef7-5777b5ecc823', '174a8fc4-56e6-3471-46d8-5777b565bf5b', '17c9c496-90a1-02f5-87bd-5777b51ab086', '1d210352-7a1f-2c5d-04ae-5777b5a3312f')");

        StringBuilder builder = new StringBuilder();
        for (String id : identifiers) {
            builder.append(" '" + id + "', ");
        }
        String builderResult = builder.toString().trim();
        builderResult = StringUtils.removeEnd(builderResult, ",");
        String query = "leads.id IN(" + builderResult + ")";

        // Since query is set, the query predicates will be ignore.
        request.getOptions().setQuery(query);
        queryPredicates.add(new QueryPredicate("last_name", Equal, "Johnson"));
        request.getOptions().setQueryPredicates(queryPredicates);

        response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Leads> readLeadsWithQuery = (List<Leads>)response.getData();
        assertNotNull(readLeadsWithQuery);

        assertEquals(identifiers.size(), readLeads.size());
        assertEquals(identifiers.size(), readLeadsWithQuery.size());
        assertEquals(readLeads.size(), readLeadsWithQuery.size());
    }

    @Test
    public void readBulkWithQuery3Test() throws ParseException {

        SugarRestRequest request = new SugarRestRequest(Cases.class);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.setRequestType(RequestType.BulkRead);
        int count = 25;
        request.getOptions().setMaxResult(count);

        // Reset options
        request.setOptions(new Options());
        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        queryPredicates.add(new QueryPredicate("name", StartsWith, "Warning"));
        queryPredicates.add(new QueryPredicate("name", Contains, "message"));
        queryPredicates.add(new QueryPredicate("status", EndsWith, "Assigned"));
        queryPredicates.add(new QueryPredicate("status", Equal, "Assigned"));

        String fromDate = "2016-06-01 00:00:00";
        String toDate = "2017-01-01 00:00:00";

        queryPredicates.add(new QueryPredicate("date_entered", QueryOperator.Between, null, fromDate, toDate));

        request.getOptions().setQueryPredicates(queryPredicates);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Cases> readCases = (List<Cases>)response.getData();
        assertNotNull(readCases);

        assertTrue(readCases.size() <= count);

        if (readCases.size() > 0) {
            boolean allConditionedValid = false;

            for (Cases item : readCases) {
                allConditionedValid = ((item.getName().startsWith("Warning")) &&
                                       (item.getName().contains("message")) &&
                                       (item.getStatus().equalsIgnoreCase("Assigned")) );

                assertTrue(allConditionedValid);
            }
        }
    }

    @Test
    public void readBulkWithPredicateTest() {

        SugarRestRequest request = new SugarRestRequest(Accounts.class, RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(10);
        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        // Since query is not set, the query predicates will be used.
        queryPredicates.add(new QueryPredicate("name", Equal, "Air Safety Inc"));
        request.getOptions().setQueryPredicates(queryPredicates);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Accounts> readAccounts = (List<Accounts>)response.getData();
        assertNotNull(readAccounts);

        // Check if all contains - "Air Safety Inc";
        if (readAccounts.size() >=  1) {
            boolean allContains = false;
            for (Accounts account : readAccounts) {
                allContains = account.getName().toLowerCase().contains("Air Safety Inc".toLowerCase());

                if (!allContains) {
                    break;
                }
            }

            assertTrue(allContains);
        }
    }

    @Test
    public void readBulkWithPredicate2Test() {

        SugarRestRequest request = new SugarRestRequest("Leads", RequestType.BulkRead);
        request.setUrl(TestAccount.Url);
        request.setUsername(TestAccount.Username);
        request.setPassword(TestAccount.Password);

        request.getOptions().setMaxResult(10);

        SugarRestClient client = new SugarRestClient();
        SugarRestResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Leads> readLeads = (List<Leads>)response.getData();
        assertNotNull(readLeads);

        // Read back with query
        // Get all identifiers
        List<String> identifiers = new ArrayList<String>();
        for (Leads lead : readLeads) {
            identifiers.add(lead.getId());
        }

        // Reset options
        request.setOptions(new Options());
        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        // Since query is not set, the query predicates will be used.
        queryPredicates.add(new QueryPredicate("id", WhereIn, identifiers.toArray()));
        request.getOptions().setQueryPredicates(queryPredicates);

        response = client.execute(request);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

        List<Leads> readLeadsWithQuery = (List<Leads>)response.getData();
        assertNotNull(readLeadsWithQuery);

        assertEquals(readLeads.size(), readLeadsWithQuery.size());
    }
}

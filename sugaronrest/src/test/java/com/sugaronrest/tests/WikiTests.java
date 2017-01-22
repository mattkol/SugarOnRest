package com.sugaronrest.tests;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.*;
import com.sugaronrest.modules.*;
import com.sugaronrest.tests.custommodels.CustomAcccount1;
import com.sugaronrest.tests.custommodels.CustomAcccount2;
import com.sugaronrest.tests.custommodels.CustomAcccount3;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sugaronrest.QueryOperator.*;

public class WikiTests {

    // @Test
    public void wikiReadLeadTest()
    {
        /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Leads;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        String moduleName = "Leads";
        String moduleId = "10a5aca7-a94b-c84a-d01a-587662da31cd";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(moduleName, RequestType.ReadById);
        request.setParameter(moduleId);

        SugarRestResponse response = client.execute(request);

        Leads lead = (Leads)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiReadContactsByPageTest()
    {
        /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Leads;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.PagedRead);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Contacts.FirstName);

        // You can mix Java type and json type.
        selectFields.add("last_name");

        request.getOptions().setSelectFields(selectFields);

        // Sets page options
        request.getOptions().setCurrentPage(1);
        request.getOptions().setNumberPerPage(10);
        SugarRestResponse response = client.execute(request);

        List<Contacts> cases = (List<Contacts>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiReadCasesTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Cases;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        String moduleName = "Cases";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(moduleName, RequestType.BulkRead);

        // Parameter can be set to null or leave unset.
        request.setParameter(null);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Cases.Id);
        selectFields.add(NameOf.Cases.Name);

        // You can mix Java type and json type.
        selectFields.add("status");
        selectFields.add("created_by");

        request.getOptions().setSelectFields(selectFields);

        // Select only 5 entities.
        // 5 is maximum, if all cases less than 5, less than 5 will be returned.
        request.getOptions().setMaxResult(5);
        SugarRestResponse response = client.execute(request);

        List<Cases> cases = (List<Cases>) response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiCreateBugTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Bugs;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        String moduleName = "Bugs";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(moduleName, RequestType.Create);

        Bugs bugToCreate = new Bugs();
        bugToCreate.setName("System crashed while running count query");
        bugToCreate.setDescription("New Oracle application server commissioning.");
        bugToCreate.setStatus("New");

        request.setParameter(bugToCreate);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Bugs.Name);
        selectFields.add(NameOf.Bugs.Description);
        selectFields.add(NameOf.Bugs.Status);

        request.getOptions().setSelectFields(selectFields);

        SugarRestResponse response = client.execute(request);

        String bugId = (String) response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiCreateBugsTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Bugs;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(Bugs.class, RequestType.BulkCreate);

        Bugs bugToCreate1 = new Bugs();
        bugToCreate1.setName("System crashed while running new photo upload.");
        bugToCreate1.setDescription("Tumblr app");
        bugToCreate1.setStatus("Pending");

        Bugs bugToCreate2 = new Bugs();
        bugToCreate2.setName("Warning is displayed in file after exporting.");
        bugToCreate2.setDescription("");
        bugToCreate2.setStatus("New");

        Bugs bugToCreate3 = new Bugs();
        bugToCreate3.setName("Fatal error during installation.");
        bugToCreate3.setDescription("Fifth floor printer.");
        bugToCreate3.setStatus("Closed");

        List<Bugs> bugsToCreate = new ArrayList<Bugs>();
        bugsToCreate.add(bugToCreate1);
        bugsToCreate.add(bugToCreate2);
        bugsToCreate.add(bugToCreate3);

        request.setParameter(bugsToCreate);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Bugs.Name);
        selectFields.add(NameOf.Bugs.Description);
        selectFields.add(NameOf.Bugs.Status);

        request.getOptions().setSelectFields(selectFields);

        SugarRestResponse response = client.execute(request);

        List<String> createdBugIds = (List<String>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiUpdateBugTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Bugs;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest(Bugs.class, RequestType.ReadById);

        String bugId = "2e1cbd26-a8ed-755b-23f1-5883cc4de3ae";
        request.setParameter(bugId);

        // Read Bugs data
        SugarRestResponse bugReadResponse = client.execute(request);
        Bugs bugToUpdate = (Bugs)bugReadResponse.getData();

        request = new SugarRestRequest(Bugs.class, RequestType.Update);

        // Update description
        bugToUpdate.setDescription("Now 7th floor printer");
        request.setParameter(bugToUpdate);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Bugs.Name);
        selectFields.add(NameOf.Bugs.Description);
        selectFields.add(NameOf.Bugs.Status);

        request.getOptions().setSelectFields(selectFields);

        SugarRestResponse response = client.execute(request);

        String updatedBugId = (String)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

   // @Test
    public void wikiUpdateLeadsTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Leads;

        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest("Leads", RequestType.BulkRead);
        request.getOptions().setMaxResult(3);

        SugarRestResponse leadsReadResponse = client.execute(request);
        List<Leads> leadsToUpdate = (List<Leads>) leadsReadResponse.getData();

        request = new SugarRestRequest(Leads.class, RequestType.BulkUpdate);

        // Update account description
        for (Leads lead : leadsToUpdate)
        {
            lead.setAccountDescription("Lead Account moved on Jan 20, 2017.");
        }

        request.setParameter(leadsToUpdate);

        // Select fields.
        List<String> selectFields = new ArrayList<String>();
        selectFields.add(NameOf.Leads.AccountName);
        selectFields.add(NameOf.Leads.AccountDescription);
        selectFields.add(NameOf.Leads.Status);

        request.getOptions().setSelectFields(selectFields);

        SugarRestResponse response = client.execute(request);

        List<String> updatedLeadIds = (List<String>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiDeleteBugTest()
    {
       /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.Bugs;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        SugarRestRequest request = new SugarRestRequest("Bugs", RequestType.Delete);
        String bugId = "2e1cbd26-a8ed-755b-23f1-5883cc4de3ae";

        request.setParameter(bugId);

        SugarRestResponse response = client.execute(request);

        String deletedBugId = (String)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void wikiBulkReadAccountTest() {
        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);

        SugarRestRequest request = new SugarRestRequest(RequestType.BulkRead);
        request.setModuleName("Leads");
        request.getOptions().setMaxResult(5);

        SugarRestResponse response = client.execute(request);

        System.out.println(response.getJData());
    }
    // @Test
    public void wikiReadLinkedAccountTest() throws IOException {

        /*
        package com.sugaronrest.tests;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.sugaronrest.*;
        import com.sugaronrest.utils.JsonObjectMapper;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);
        String accountId = "2da8333f-10b8-d173-e38c-587662482d83";

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

        SugarRestResponse response = client.execute(request);

        String jsonData = response.getJData();

        System.out.println(jsonData);
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        CustomAcccount1 customAccount = mapper.readValue(jsonData, CustomAcccount1.class);
    }

    // @Test
    public void bulkLinkedRead2Test() throws IOException {
        /*
        package com.sugaronrest.tests;

        import com.fasterxml.jackson.databind.JavaType;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.sugaronrest.*;
        import com.sugaronrest.modules.*;
        import com.sugaronrest.tests.custommodels.CustomAcccount2;
        import com.sugaronrest.utils.JsonObjectMapper;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);

        SugarRestRequest request = new SugarRestRequest(RequestType.LinkedBulkRead);
        request.setModuleName("Accounts");
        request.getOptions().setMaxResult(5);

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

        SugarRestResponse response = client.execute(request);

        String jsonData = response.getJData();

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CustomAcccount2.class) ;
        List<CustomAcccount2> customAccounts = mapper.readValue(jsonData, type);

        System.out.println(jsonData);
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void bulkLinkedItemsReadTest() throws IOException {
        /*
        package com.sugaronrest.tests;

        import com.fasterxml.jackson.databind.JavaType;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.sugaronrest.*;
        import com.sugaronrest.modules.*;
        import com.sugaronrest.tests.custommodels.CustomAcccount3;
        import com.sugaronrest.utils.JsonObjectMapper;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient(url, username, password);

        SugarRestRequest request = new SugarRestRequest(RequestType.LinkedBulkRead);
        request.setModuleName("Accounts");
        request.getOptions().setMaxResult(5);

        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Accounts.Id);
        selectedFields.add(NameOf.Accounts.Name);
        selectedFields.add(NameOf.Accounts.Industry);
        selectedFields.add(NameOf.Accounts.Website);
        selectedFields.add(NameOf.Accounts.ShippingAddressCity);

        request.getOptions().setSelectFields(selectedFields);

        Map<Object, List<String>> linkedListInfo = new HashMap<Object, List<String>>();

        linkedListInfo.put(Cases.class, null);
        linkedListInfo.put("Contacts", new ArrayList<String>());
        request.getOptions().setLinkedModules(linkedListInfo);

        SugarRestResponse response = client.execute(request);

        String jsonData = response.getJData();

        // Deserialize json data to custom object
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, CustomAcccount3.class) ;
        List<CustomAcccount3> customAccounts = mapper.readValue(jsonData, type);

        System.out.println(jsonData);
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void readBulkWithQuery1Test() throws ParseException {

        /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.*;

        import java.util.ArrayList;
        import java.util.List;

        import static com.sugaronrest.QueryOperator.*;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient();

        SugarRestRequest request = new SugarRestRequest(Cases.class);
        request.setUrl(url);
        request.setUsername(username);
        request.setPassword(password);

        request.setRequestType(RequestType.BulkRead);

        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        queryPredicates.add(new QueryPredicate(NameOf.Cases.Name, Equal, "System not responding"));
        queryPredicates.add(new QueryPredicate("status", Equal, "Open_New"));

        String fromDate = "2016-06-01 00:00:00";
        String toDate = "2017-01-20 00:00:00";

        queryPredicates.add(new QueryPredicate(NameOf.Cases.DateEntered, QueryOperator.Between, null, fromDate, toDate));

        request.getOptions().setQueryPredicates(queryPredicates);
        SugarRestResponse response = client.execute(request);

        List<Cases> readCases = (List<Cases>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void readBulkWithQuery2Test() throws ParseException {

        /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.*;

        import java.util.ArrayList;
        import java.util.List;

        import static com.sugaronrest.QueryOperator.*;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient();

        SugarRestRequest request = new SugarRestRequest(Leads.class);
        request.setUrl(url);
        request.setUsername(username);
        request.setPassword(password);

        request.setRequestType(RequestType.BulkRead);

        String query = "leads.id IN('10a5aca7-a94b-c84a-d01a-587662da31cd', '118f5999-4e81-5510-8c00-587662372d02', '1304e89b-942b-5866-2aad-587662f632d5')";
        request.getOptions().setQuery(query);

        SugarRestResponse response = client.execute(request);

        List<Leads> readLeads = (List<Leads>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }

    // @Test
    public void readBulkWithQuery3Test() throws ParseException {

        /*
        package com.sugaronrest.tests;

        import com.sugaronrest.*;
        import com.sugaronrest.modules.*;

        import java.util.ArrayList;
        import java.util.List;

        import static com.sugaronrest.QueryOperator.*;
        */

        String url = "http://demo.suiteondemand.com/service/v4_1/rest.php";
        String username = "will";
        String password = "will";

        SugarRestClient client = new SugarRestClient();

        SugarRestRequest request = new SugarRestRequest(Cases.class);
        request.setUrl(url);
        request.setUsername(username);
        request.setPassword(password);

        request.setRequestType(RequestType.BulkRead);

        String query = "cases.name = 'Need assistance with large customization'";
        request.getOptions().setQuery(query);

        List<QueryPredicate> queryPredicates = new ArrayList<QueryPredicate>();

        queryPredicates.add(new QueryPredicate(NameOf.Cases.Name, Equal, "System not responding"));
        queryPredicates.add(new QueryPredicate("status", Equal, "Open_New"));

        String fromDate = "2016-06-01 00:00:00";
        String toDate = "2017-01-20 00:00:00";

        // Since query was set, the predicates will be ignored.
        queryPredicates.add(new QueryPredicate(NameOf.Cases.DateEntered, QueryOperator.Between, null, fromDate, toDate));

        request.getOptions().setQueryPredicates(queryPredicates);
        SugarRestResponse response = client.execute(request);

        List<Cases> readCases = (List<Cases>)response.getData();

        System.out.println(response.getJData());
        System.out.println(response.getJsonRawRequest());
        System.out.println(response.getJsonRawResponse());
    }
}

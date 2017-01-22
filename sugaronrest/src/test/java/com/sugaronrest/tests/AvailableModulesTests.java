package com.sugaronrest.tests;

import com.sugaronrest.RequestType;
import com.sugaronrest.SugarRestClient;
import com.sugaronrest.SugarRestRequest;
import com.sugaronrest.SugarRestResponse;
import com.sugaronrest.utils.ModuleMapper;
import org.apache.http.HttpStatus;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AvailableModulesTests {

    //@Test
    public void getAllModules() {
        SugarRestClient client = new SugarRestClient(TestAccount.Url, TestAccount.Username, TestAccount.Password);
        SugarRestRequest request = new SugarRestRequest(RequestType.AllModulesRead);

        SugarRestResponse response = client.execute(request);
        List<String> allAvailableModules = (List<String>)response.getData();

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        assertTrue(allAvailableModules.size() > 0);

        // Get all mapped modules
        List<String> allMappedModules = ModuleMapper.getInstance().getAllModules();

        int count = 0;
        for (String module : allMappedModules) {
            // Do a bulk read module test
            response = bulkReadModule(client, module);
            // assertNotNull(response);
            // assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

            int statusOk = (int)HttpStatus.SC_OK;
            int statusBadRequest = (int)HttpStatus.SC_BAD_REQUEST;
            int statusOInternalError = (int)HttpStatus.SC_INTERNAL_SERVER_ERROR;

            if (response.getStatusCode() == statusOk) {
                System.out.println("Module name:" + module + ":::: Status" + response.getStatusCode());
                System.out.println(response.getJsonRawResponse());
                System.out.println(response.getError().getTrace());
                count = count + 1;
            }
        }

        System.out.println("Total Count::::" + allMappedModules.size());
        System.out.println("Count::::" + count);

        /*
        for (String module : allAvailableModules) {
            if (!allMappedModules.contains(module) ) {
                // Do a bulk read module test
                response = bulkReadModule(client, module);
               // assertNotNull(response);
               // assertEquals(response.getStatusCode(), HttpStatus.SC_OK);

                if (response.getStatusCode() == (int)HttpStatus.SC_BAD_REQUEST) {
                    System.out.println("Module name:" + module + ":::: Status" + response.getStatusCode());
                    System.out.println(response.getJsonRawResponse());
                    System.out.println(response.getError().getTrace());
                }
            }
        }
        */
    }

    private static SugarRestResponse bulkReadModule(SugarRestClient client, String moduleName) {
        SugarRestRequest request = new SugarRestRequest(moduleName, RequestType.BulkRead);
        request.getOptions().setMaxResult(5);
        return client.execute(request);
    }

}

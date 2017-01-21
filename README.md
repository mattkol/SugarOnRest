# Being RESTful with SugarCRM/SuiteCRM in Java 
SugarOnRest is a Java SugarCRM CE 6.x/SuiteCRM 7.x API client. SugarOnRest is a [UniRest Java](https://github.com/Mashape/unirest-java) implementation. It is a Restful CRUD client that implements the SugarCRM module Create, Read, Update and Delete functionalities.

SugarOnRest implements following SugarCRM REST API method calls: **_oauth_access, get_entry, get_entry_list, set_entry, set_entries._**

This is a port of .NET C# [SugarRestSharp](https://github.com/mattkol/SugarRestSharp).

For more info/documentation, please check [SugarOnRest wiki](https://github.com/mattkol/SugarOnRest/wiki)

### Basic Sample Usages
```java
string sugarCrmUrl = "http://demo.suiteondemand.com/service/v4_1/rest.php";
string sugarCrmUsername = "will";
string sugarCrmPassword = "will";

SugarRestClient client = new SugarRestClient(sugarCrmUrl, sugarCrmUsername, sugarCrmPassword);

// Option 1 - Read by known Java Pojo type - Accounts.
SugarRestRequest accountRequest = new SugarRestRequest(Accounts.class, RequestType.ReadById);

// set the account id to read.
accountRequest.setParameter("2da8333f-10b8-d173-e38c-587662482d83");
SugarRestResponse accountResponse = client.execute(accountRequest);
Accounts account = (Accounts)accountResponse.getData();


// Option 2 - Read by known SugarCRM module name - "Contacts".
SugarRestRequest contactRequest = new SugarRestRequest("Contacts", RequestType.ReadById);
contactRequest.setParameter(contactid);
SugarRestResponse contactRresponse = client.execute(contactRequest);
Contacts contact = (Contacts)contactRresponse.getData();

```

<br />
### Advanced Sample Usage - Linked Module
This sample usage shows how to read "Accounts" module entity data with linked modules (link "Contacts" module). For more request options make changes to the [Options parameter](https://github.com/mattkol/SugarRestSharp/wiki/Request%20Options).

This implements the **_get_entry_** SugarCRM REST API method setting the **_link_name_to_fields_array_** parameter.

```java
package com.sugaronrest.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.*;
import com.sugaronrest.utils.JsonObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

```

### Custom model
```java 
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
```

### Response (Data)
```java 
getData() = null;

// Deserialize json data to custom object
ObjectMapper mapper = JsonObjectMapper.getMapper();
CustomAcccount1 customAccount = mapper.readValue(jsonData, CustomAcccount1.class);
```

### Response (JData)
```java 
{
  "website": "www.veganthe.co.jp",
  "name": "RR. Talker Co",
  "industry": "Media",
  "id": "2da8333f-10b8-d173-e38c-587662482d83",
  "shipping_address_city": "St. Petersburg",
  "contacts": [
    {
      "primary_address_postalcode": "24688",
      "last_name": "Bhatt",
      "description": "",
      "title": "IT Developer",
      "first_name": "Corina"
    },
    {
      "primary_address_postalcode": "39916",
      "last_name": "Lehner",
      "description": "",
      "title": "Senior Product Manager",
      "first_name": "Ramiro"
    },
    {
      "primary_address_postalcode": "38602",
      "last_name": "Delorenzo",
      "description": "",
      "title": "Senior Product Manager",
      "first_name": "Kelley"
    },
    {
      "primary_address_postalcode": "81029",
      "last_name": "Paulin",
      "description": "",
      "title": "VP Operations",
      "first_name": "Sheree"
    },
    {
      "primary_address_postalcode": "91550",
      "last_name": "Clopton",
      "description": "",
      "title": "IT Developer",
      "first_name": "Jacqueline"
    },
    {
      "primary_address_postalcode": "19042",
      "last_name": "Hong",
      "description": "",
      "title": "IT Developer",
      "first_name": "Juanita"
    },
    {
      "primary_address_postalcode": "29452",
      "last_name": "Pagano",
      "description": "",
      "title": "IT Developer",
      "first_name": "Lamar"
    },
    {
      "primary_address_postalcode": "88939",
      "last_name": "Cossey",
      "description": "",
      "title": "President",
      "first_name": "Bernadine"
    },
    {
      "primary_address_postalcode": "40382",
      "last_name": "Bast",
      "description": "",
      "title": "Director Sales",
      "first_name": "Paul"
    }
  ]
}
```

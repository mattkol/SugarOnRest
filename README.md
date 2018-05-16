# Being RESTful with SugarCRM/SuiteCRM in Java 
SugarOnRest is a Java SugarCRM CE 6.x/SuiteCRM 7.x API client. SugarOnRest is a [UniRest Java](https://github.com/Mashape/unirest-java) implementation. It is a Restful CRUD client that implements the SugarCRM module Create, Read, Update and Delete functionalities.

SugarOnRest implements following SugarCRM REST API method calls: **_oauth_access, get_entry, get_entry_list, set_entry, set_entries._**

This is a port of .NET C# [SugarRestSharp](https://github.com/mattkol/SugarRestSharp).

For more info/documentation, please check [SugarOnRest wiki](https://github.com/mattkol/SugarOnRest/wiki)

### Basic Sample Usages
```java
String sugarCrmUrl = "http://demo.suiteondemand.com/service/v4_1/rest.php";
String sugarCrmUsername = "will";
String sugarCrmPassword = "will";

SugarRestClient client = new SugarRestClient(sugarCrmUrl, sugarCrmUsername, sugarCrmPassword);

// Option 1 - Read by known Java Pojo type - Accounts.
SugarRestRequest accountRequest = new SugarRestRequest(Accounts.class, RequestType.ReadById);

// set the account id to read.
accountRequest.setParameter("2da8333f-10b8-d173-e38c-587662482d83");
SugarRestResponse accountResponse = client.execute(accountRequest);
Accounts account = (Accounts)accountResponse.getData();


// Option 2 - Read by known SugarCRM module name - "Contacts".
SugarRestRequest contactRequest = new SugarRestRequest("Contacts", RequestType.ReadById);
contactRequest.setParameter("1b680648-20ca-cd20-692e-584fbec623e5");
SugarRestResponse contactRresponse = client.execute(contactRequest);
Contacts contact = (Contacts)contactRresponse.getData();

```

### Advanced Sample Usage - Linked Module
This sample usage shows how to read "Accounts" module entity data with linked modules (link "Contacts" module). For more request options make changes to the
[Request Options](https://github.com/mattkol/SugarOnRest/wiki/Request-Options) 


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

### Response (getData())
```java 
getData() = null;

// Deserialize json data to custom object
ObjectMapper mapper = JsonObjectMapper.getMapper();
CustomAcccount1 customAccount = mapper.readValue(jsonData, CustomAcccount1.class);
```

### Response (getJData())
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


### Response (getJsonRawRequest())
```java 
{
  "method": "get_entry",
  "input_type": "json",
  "response_type": "json",
  "rest_data": {
    "session": "2p7qi9qpvjvic3et2t5m410e31",
    "module_name": "Accounts",
    "id": "2da8333f-10b8-d173-e38c-587662482d83",
    "select_fields": [
      "id",
      "name",
      "industry",
      "website",
      "shipping_address_city"
    ],
    "link_name_to_fields_array": [
      {
        "name": "contacts",
        "value": [
          "first_name",
          "last_name",
          "title",
          "description",
          "primary_address_postalcode"
        ]
      }
    ],
    "track_view": false
  }
}
```

### Response (getJsonRawResponse())
```java 
{
  "entry_list": [
    {
      "id": "2da8333f-10b8-d173-e38c-587662482d83",
      "module_name": "Accounts",
      "name_value_list": {
        "id": {
          "name": "id",
          "value": "2da8333f-10b8-d173-e38c-587662482d83"
        },
        "name": {
          "name": "name",
          "value": "RR. Talker Co"
        },
        "industry": {
          "name": "industry",
          "value": "Media"
        },
        "website": {
          "name": "website",
          "value": "www.veganthe.co.jp"
        },
        "shipping_address_city": {
          "name": "shipping_address_city",
          "value": "St. Petersburg"
        }
      }
    }
  ],
  "relationship_list": [
    [
      {
        "name": "contacts",
        "records": [
          {
            "first_name": {
              "name": "first_name",
              "value": "Corina"
            },
            "last_name": {
              "name": "last_name",
              "value": "Bhatt"
            },
            "title": {
              "name": "title",
              "value": "IT Developer"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "24688"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Ramiro"
            },
            "last_name": {
              "name": "last_name",
              "value": "Lehner"
            },
            "title": {
              "name": "title",
              "value": "Senior Product Manager"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "39916"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Kelley"
            },
            "last_name": {
              "name": "last_name",
              "value": "Delorenzo"
            },
            "title": {
              "name": "title",
              "value": "Senior Product Manager"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "38602"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Sheree"
            },
            "last_name": {
              "name": "last_name",
              "value": "Paulin"
            },
            "title": {
              "name": "title",
              "value": "VP Operations"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "81029"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Jacqueline"
            },
            "last_name": {
              "name": "last_name",
              "value": "Clopton"
            },
            "title": {
              "name": "title",
              "value": "IT Developer"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "91550"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Juanita"
            },
            "last_name": {
              "name": "last_name",
              "value": "Hong"
            },
            "title": {
              "name": "title",
              "value": "IT Developer"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "19042"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Lamar"
            },
            "last_name": {
              "name": "last_name",
              "value": "Pagano"
            },
            "title": {
              "name": "title",
              "value": "IT Developer"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "29452"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Bernadine"
            },
            "last_name": {
              "name": "last_name",
              "value": "Cossey"
            },
            "title": {
              "name": "title",
              "value": "President"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "88939"
            }
          },
          {
            "first_name": {
              "name": "first_name",
              "value": "Paul"
            },
            "last_name": {
              "name": "last_name",
              "value": "Bast"
            },
            "title": {
              "name": "title",
              "value": "Director Sales"
            },
            "description": {
              "name": "description",
              "value": ""
            },
            "primary_address_postalcode": {
              "name": "primary_address_postalcode",
              "value": "40382"
            }
          }
        ]
      }
    ]
  ]
}
```


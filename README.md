# Being RESTful with SugarCRM in Java 
SugarOnRest is a Java SugarCRM CE 6.5 API client. SugarOnRest is a UniRest implementation. It is a Restful CRUD client that implements the SugarCRM module Create, Read, Update and Delete functionalities.

SugarOnRest implements following SugarCRM REST API method calls: **_oauth_access, get_entry, get_entry_list, set_entry, set_entries._**

This is a port of .NET C# [SugarRestSharp](https://github.com/mattkol/SugarRestSharp).

For more info/documentation, please check [SugarOnRest wiki](https://github.com/mattkol/SugarOnRest/wiki)

### Basic Sample Usages
```java
string sugarCrmUrl = "http://191.101.224.189/sugar/service/v4_1/rest.php";
string sugarCrmUsername = "will";
string sugarCrmPassword = "will";

SugarRestClient client = new SugarRestClient(sugarCrmUrl, sugarCrmUsername, sugarCrmPassword);

// Option 1 - Read by known type Accounts.
SugarRestRequest accountRequest = new SugarRestRequest(Accounts.class, RequestType.ReadById);

// set the account id to read.
accountRequest.setId("1f2d3240-0d8a-ca09-2e11-5777c29a4193");
SugarRestResponse accountResponse = client.execute(accountRequest);
Accounts account = (Accounts)accountResponse.getData();


// Option 2 - Read by known SugarCRM module name - "Contacts".
SugarRestRequest contactRequest = new SugarRestRequest("Contacts", RequestType.ReadById);
contactRequest.Id = contactid;
SugarRestResponse contactRresponse = client.Execute(contactRequest);
Contacts contact = (Contacts)contactRresponse.getData();

```



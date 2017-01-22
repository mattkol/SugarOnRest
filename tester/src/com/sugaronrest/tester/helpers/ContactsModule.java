package com.sugaronrest.tester.helpers;

import com.sugaronrest.*;
import com.sugaronrest.modules.Contacts;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kolao_000 on 2017-01-13.
 */
public class ContactsModule {
    public static SugarRestResponse createContact(SugarRestClient client, Contacts contact) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Create);
        request.setParameter(contact);
        request.getOptions().setSelectFields(getSelectedField());

        return client.execute(request);
    }

    public static SugarRestResponse createContactByType(SugarRestClient client, Contacts contact)
    {
        SugarRestRequest request = new SugarRestRequest(RequestType.Create);
        request.setModuleType(Contacts.class);
        request.setParameter(contact);
        request.getOptions().setSelectFields(getSelectedField());

        return client.execute(request);
    }


    public static SugarRestResponse bulkCreateContact(SugarRestClient client, List<Contacts> contacts) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkCreate);
        request.setParameter(contacts);
        request.getOptions().setSelectFields(getSelectedField());

        return client.execute(request);
    }

    public static SugarRestResponse readContact(SugarRestClient client, String contactId) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.ReadById);
        request.setParameter(contactId);
        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

        return client.execute(request);
    }

    public static SugarRestResponse readContactByType(SugarRestClient client, String contactId) {
        SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.ReadById);
        request.setParameter(contactId);
        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

        return client.execute(request);
    }

    public static SugarRestResponse bulkReadContact(SugarRestClient client, int count) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkRead);
        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

        request.getOptions().setMaxResult(count);

        return client.execute(request);
    }

    public static List<Contacts> bulkReadContact2(SugarRestClient client, List<String> contactIds) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.ReadById);
        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Id);

        List<Contacts> contacts = new ArrayList<Contacts>();

        for (String id : contactIds)
        {
            request.setParameter(id);
            SugarRestResponse response = client.execute(request);

            contacts.add((Contacts)response.getData());
        }

        return contacts;
    }

    public static SugarRestResponse updateContact(SugarRestClient client, String identifier) {
        Random random = new Random();
        int uniqueNumber = 10000 + random.nextInt(1000);

        Contacts contact = new Contacts();
        contact.setId(identifier);
        contact.setTitle("Vice President of Programming");

        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Update);
        request.setParameter(contact);

        request.getOptions().setSelectFields(new ArrayList<String>());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Title);

        return client.execute(request);
    }

    public static SugarRestResponse bulkUpdateContact(SugarRestClient client, List<Contacts> contacts) {
        Random random = new Random();
        for (Contacts contact : contacts)
        {
            contact.setPrimaryAddressPostalcode(10000 + random.nextInt(1000) + "");
        }

        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.BulkUpdate);
        request.setParameter(contacts);

        request.getOptions().setSelectFields(new ArrayList<String>());
        request.getOptions().getSelectFields().add(NameOf.Contacts.PrimaryAddressPostalcode);

        return client.execute(request);
    }

    public static SugarRestResponse deleteContact(SugarRestClient client, String contactId) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Delete);
        request.setParameter(contactId);

        return client.execute(request);
    }

    public static SugarRestResponse deleteContactByType(SugarRestClient client, String contactId) {
        SugarRestRequest request = new SugarRestRequest(Contacts.class, RequestType.Delete);
        request.setParameter(contactId);

        return client.execute(request);
    }

    public static List<String> bulkDeleteContact(SugarRestClient client, List<String> contactIds) {
        SugarRestRequest request = new SugarRestRequest("Contacts", RequestType.Delete);

        List<String> listId = new ArrayList<String>();
        for (String id : contactIds){
            request.setParameter(id);
            SugarRestResponse response = client.execute(request);
            String identifier = (response.getData() == null) ? StringUtils.EMPTY : response.getData().toString();
            listId.add(identifier);
        }

        return listId;
    }

    public static List<String> getSelectedField()
    {
        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add(NameOf.Contacts.FirstName);
        selectedFields.add(NameOf.Contacts.LastName);
        selectedFields.add(NameOf.Contacts.Title);
        selectedFields.add(NameOf.Contacts.Description);
        selectedFields.add(NameOf.Contacts.PrimaryAddressPostalcode);

        return selectedFields;
    }

    public static List<String> getJsonSelectedField()
    {
        List<String> selectedFields = new ArrayList<String>();

        selectedFields.add("id");
        selectedFields.add("first_name");
        selectedFields.add("last_name");
        selectedFields.add("title");
        selectedFields.add("description");
        selectedFields.add("primary_address_postalcode");

        return selectedFields;
    }

    public static Contacts getTestContact()
    {
        Contacts contact = new Contacts();
        contact.setFirstName("Carolyn");
        contact.setLastName("Smith");
        contact.setTitle("Director of Programming");
        contact.setDescription("Likely lead for next project");
        contact.setPrimaryAddressPostalcode("65554");

        return contact;
    }

    public static List<Contacts> getTestBulkContact() {
        Random random = new Random();

        List<Contacts> contacts = new ArrayList<Contacts>();

        for (int i = 0; i < 5; i++)
        {
            Contacts contact = new Contacts();
            int uniqueNumber = 10000 + random.nextInt(1000);
            contact.setFirstName("FirstName_" + uniqueNumber);
            contact.setLastName("LastName_" + uniqueNumber);
            contact.setTitle("Title_" + uniqueNumber);
            contact.setPrimaryAddressPostalcode(uniqueNumber + "");

            contacts.add(contact);
        }

        return contacts;
    }
}

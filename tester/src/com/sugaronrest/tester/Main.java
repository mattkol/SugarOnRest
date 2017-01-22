package com.sugaronrest.tester;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) {

        accountsModuleTests();
        contactsModuleTests();
        linkedModulesTests();
        queryTests();
    }

    private static void accountsModuleTests() {
        try {
            System.out.println(">>>>> Start AccountsModuleTests.crudTest()");
            AccountsModuleTests.crudTest();
            System.out.println(">>>>> End AccountsModuleTests.crudTest()");

            System.out.println(">>>>> Start AccountsModuleTests.bulkCRUDTest()");
            AccountsModuleTests.bulkCRUDTest();
            System.out.println(">>>>> End AccountsModuleTests.bulkCRUDTest()");

            System.out.println(">>>>> Start AccountsModuleTests.deleteGroupAccountTest()");
            AccountsModuleTests.deleteGroupAccountTest();
            System.out.println(">>>>> End AccountsModuleTests.deleteGroupAccountTest()");

            System.out.println(">>>>> Start AccountsModuleTests.readAccountSelectFieldTest()");
            AccountsModuleTests.readAccountSelectFieldTest();
            System.out.println(">>>>> End AccountsModuleTests.readAccountSelectFieldTest()");

            System.out.println(">>>>> Start AccountsModuleTests.readBulkAccountTest()");
            AccountsModuleTests.readBulkAccountTest();
            System.out.println(">>>>> End AccountsModuleTests.readBulkAccountTest()");

            System.out.println(">>>>> Start AccountsModuleTests.readPagedAccountTest()");
            AccountsModuleTests.readPagedAccountTest();
            System.out.println(">>>>> End AccountsModuleTests.readPagedAccountTest()");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void contactsModuleTests() {
        System.out.println(">>>>> Start ContactsModuleTests.crudTest()");
        ContactsModuleTests.crudTest();
        System.out.println(">>>>> End ContactsModuleTests.crudTest()");

        System.out.println(">>>>> Start ContactsModuleTests.bulkCRUDTest()");
        ContactsModuleTests.bulkCRUDTest();
        System.out.println(">>>>> End ContactsModuleTests.bulkCRUDTest()");

        System.out.println(">>>>> Start ContactsModuleTests.readBulkTest()");
        ContactsModuleTests.readBulkTest();
        System.out.println(">>>>> End ContactsModuleTests.readBulkTest()");

        System.out.println(">>>>> Start ContactsModuleTests.crudByTypeTest()");
        ContactsModuleTests.crudByTypeTest();
        System.out.println(">>>>> End ContactsModuleTests.crudByTypeTest()");
    }

    private static void linkedModulesTests() {

        try {

            System.out.println(">>>>> Start LinkedModulesTests.bulkLinkedRead1Test()");
            LinkedModulesTests.bulkLinkedRead1Test();
            System.out.println(">>>>> End LinkedModulesTests.bulkLinkedRead1Test()");

            System.out.println(">>>>> Start LinkedModulesTests.linkedRead1Test()");
            LinkedModulesTests.linkedRead1Test();
            System.out.println(">>>>> End LinkedModulesTests.linkedRead1Test()");

            System.out.println(">>>>> Start LinkedModulesTests.linkedRead2Test()");
            LinkedModulesTests.linkedRead2Test();
            System.out.println(">>>>> End LinkedModulesTests.linkedRead2Test()");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void queryTests() {
        try {

            System.out.println(">>>>> Start QueryTests.readBulkWithPredicate2Test()");
            QueryTests.readBulkWithPredicate2Test();
            System.out.println(">>>>> End QueryTests.readBulkWithPredicate2Test()");

            System.out.println(">>>>> Start QueryTests.readBulkWithPredicateTest()");
            QueryTests.readBulkWithPredicateTest();
            System.out.println(">>>>> End QueryTests.readBulkWithPredicateTest()");

            System.out.println(">>>>> Start QueryTests.readBulkWithQuery1Test()");
            QueryTests.readBulkWithQuery1Test();
            System.out.println(">>>>> End QueryTests.readBulkWithQuery1Test()");

            System.out.println(">>>>> Start QueryTests.readBulkWithQuery2Test()");
            QueryTests.readBulkWithQuery2Test();
            System.out.println(">>>>> End QueryTests.readBulkWithQuery2Test()");

            System.out.println(">>>>> Start QueryTests.readBulkWithQuery3Test()");
            QueryTests.readBulkWithQuery3Test();
            System.out.println(">>>>> End QueryTests.readBulkWithQuery3Test()");

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}

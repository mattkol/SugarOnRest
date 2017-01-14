package com.sugaronrest.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sugaronrest.utils.JsonObjectMapper;

/**
 * Created by kolao_000 on 2017-01-05.
 */
public final class TestAccount {

    public static String Url = "http://191.101.224.189/sugar/service/v4_1/rest.php";
    public static String Username = "will";
    public static String Password = "will";

    public static void printJsonObject(Object obj) throws JsonProcessingException {
        printJsonObject(obj.getClass().getSimpleName(), obj);
    }

    public static void printJsonObject(String title, Object obj) throws JsonProcessingException {
        System.out.println("---------" + title + "-------------");
        ObjectMapper mapper = JsonObjectMapper.getMapper();
        String jsonError = mapper.writeValueAsString(obj);
        System.out.println(jsonError);
    }
}

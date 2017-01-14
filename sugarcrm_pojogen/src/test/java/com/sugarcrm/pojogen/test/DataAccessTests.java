package com.sugarcrm.pojogen.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Created by kolao_000 on 2016-12-21.
 */
public class DataAccessTests {

    @Before
    public void setUp() throws Exception {

    }

        @Test
    public void tableSchemaTest()  {

        try
        {
            String username = "root";
            String password = "pass4word01";

            String jdbcdriver = "com.mysql.jdbc.Driver";
            String jdbcurl = "jdbc:mysql://localhost:3306/SugarCrm";
            Class.forName(jdbcdriver);

            java.sql.Connection connection = DriverManager.getConnection(jdbcurl, username,password);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = 	"SELECT COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_KEY, IS_NULLABLE, EXTRA " +
                    "FROM information_schema.columns " +
                    "WHERE (table_schema='sugarcrm' AND table_name='accounts')";


            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println(columnsNumber);
            System.out.println("");
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.println(rsmd.getColumnName(i));
                    System.out.println(columnValue);
                }
                System.out.println("");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void dbSchemaTest()  {

        try
        {
            String username = "root";
            String password = "pass4word01";

            String jdbcdriver = "com.mysql.jdbc.Driver";
            String jdbcurl = "jdbc:mysql://localhost:3306/SugarCrm";
            Class.forName(jdbcdriver);

            java.sql.Connection connection = DriverManager.getConnection(jdbcurl, username,password);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = 	"SELECT TABLE_NAME, TABLE_SCHEMA, TABLE_TYPE " +
                            "FROM information_schema.tables " +
                            "WHERE (table_type='BASE TABLE' OR table_type='VIEW') AND TABLE_SCHEMA=DATABASE()";

            ResultSet resultSet = statement.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println(columnsNumber);
            System.out.println("");
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.println(rsmd.getColumnName(i));
                    System.out.println(columnValue);
                }
                System.out.println("");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {

    }

}
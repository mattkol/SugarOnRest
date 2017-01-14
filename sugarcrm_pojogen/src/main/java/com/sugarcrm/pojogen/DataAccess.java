package com.sugarcrm.pojogen;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by kolao_000 on 2016-12-29.
 */
public final class DataAccess {
    public static ResultSet getTableResultSet(Account account, String schema, String tablename)  {
        try
        {
            String query = 	"SELECT COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_KEY, IS_NULLABLE, EXTRA " +
                    "FROM information_schema.columns " +
                    "WHERE (table_schema='" + schema + "' AND table_name='" + tablename + "')";

            return getResultSet(account, query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getSchemaResultSet(Account account)  {
        try
        {
            String query = 	"SELECT TABLE_NAME, TABLE_SCHEMA, TABLE_TYPE " +
                    "FROM information_schema.tables " +
                    "WHERE (table_type='BASE TABLE' OR table_type='VIEW') AND TABLE_SCHEMA=DATABASE()";

            return getResultSet(account, query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static ResultSet getResultSet(Account account, String query)  {
        try
        {
            String username = account.getUsername();
            String password = account.getPassword();
            String url = account.getUrl();
            String port = account.getPort();
            String databaseName = account.getDatabaseName();

            String jdbcdriver = "com.mysql.jdbc.Driver";
            String jdbcurl = "jdbc:mysql://" + url + ":" + port + "/" + databaseName;
            Class.forName(jdbcdriver);

            java.sql.Connection connection = DriverManager.getConnection(jdbcurl, username,password);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            return statement.executeQuery(query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

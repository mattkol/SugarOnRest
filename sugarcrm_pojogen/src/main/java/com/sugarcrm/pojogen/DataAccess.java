/**
 MIT License

 Copyright (c) 2017 Kola Oyewumi

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package com.sugarcrm.pojogen;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public final class DataAccess {

    /**
     * Gets table collection resultset.
     *
     * @param account The credential object.
     * @param queryData Set of the query data obects.
     * @param schema The schema name.
     * @param tablename The table name.
     * @return ResultSet object.
     */
    public static ResultSet getTableResultSet(Account account, SqlQueryData queryData, String schema, String tablename)  {
        try
        {
            String query = 	"SELECT COLUMN_NAME, COLUMN_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_KEY, IS_NULLABLE, EXTRA " +
                    "FROM information_schema.columns " +
                    "WHERE (table_schema='" + schema + "' AND table_name='" + tablename + "')";

            return getResultSet(account, queryData, query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the schema info resultset.
     *
     * @param account The credential object.
     * @param queryData Set of the query data obects.
     * @return ResultSet object.
     */
    public static ResultSet getSchemaResultSet(Account account, SqlQueryData queryData)  {
        try
        {
            String query = 	"SELECT TABLE_NAME, TABLE_SCHEMA, TABLE_TYPE " +
                    "FROM information_schema.tables " +
                    "WHERE (table_type='BASE TABLE' OR table_type='VIEW') AND TABLE_SCHEMA=DATABASE()";

            return getResultSet(account, queryData, query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets SugarCRM module relationship resultset.
     *
     * @param account The credential object.
     * @param queryData Set of the query data obects.
     * @return ResultSet object.
     */
    public static ResultSet getRelationshipResultSet(Account account, SqlQueryData queryData)  {
        try
        {
            String query = 	"SELECT LHS_MODULE, LHS_TABLE, RHS_MODULE, RHS_TABLE FROM sugarcrm.relationships ";

            return getResultSet(account, queryData, query);

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the resultset based on the query.
     *
     * @param account The credential object.
     * @param queryData Set of the query data obects.
     * @param query The query string.
     * @return ResultSet object.
     */
    private static ResultSet getResultSet(Account account, SqlQueryData queryData, String query)  {
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

            queryData.statatemet = statement;
            queryData.connection = connection;
            ResultSet resultSet = statement.executeQuery(query);

            queryData.resultSet = resultSet;
            return resultSet;

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

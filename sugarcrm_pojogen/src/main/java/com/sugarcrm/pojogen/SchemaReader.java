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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SchemaReader {

    /**
     * Gets the table info of the schema.
     *
     * @param account The credential object.
     * @return Table object collection.
     */
    public List<Table> getSchemaTables(Account account)  {
        List<Table> tables = new ArrayList<Table>();
        SqlQueryData queryData =  new SqlQueryData();

        try
        {
            ResultSet resultSet = DataAccess.getSchemaResultSet(account, queryData);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                String schema = resultSet.getString("TABLE_SCHEMA");
                String tableName = resultSet.getString("TABLE_NAME");
                String tableType = resultSet.getString("TABLE_TYPE");

                Table table = new Table();
                table.setName(tableName);
                table.setSchema(schema);

                String className = Utils.toPascalCase(tableName);
                table.setClassName(className);

                boolean isView = Utils.getIsView(tableType);
                table.setIsView(isView);

                List<Column> columns = getTableColumns(account, schema, tableName);
                table.setColumns(columns);

                List<String> uniquePackages = new ArrayList<String>();
                for (Column column : columns) {
                    String packageName = Utils.getExtraPackage(column.getPropertyType());
                    if (!packageName.equalsIgnoreCase("")) {
                        if (!uniquePackages.contains(packageName)) {
                            uniquePackages.add(packageName);
                        }
                    }
                }

                table.setExtraPackages(uniquePackages);
                tables.add(table);
            }

        } catch (Exception e){
            queryData.closeResources();
            e.printStackTrace();
        }

        queryData.closeResources();
        return tables;
    }

    /**
     * Gets table columns.
     *
     * @param account The credential object.
     * @param schema The schema name.
     * @param tablename The table name.
     * @return Columns collection.
     */
    public List<Column> getTableColumns(Account account, String schema, String tablename)  {

        List<Column> columns = new ArrayList<Column>();
        SqlQueryData queryData =  new SqlQueryData();

        try
        {
            ResultSet resultSet = DataAccess.getTableResultSet(account, queryData, schema, tablename);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                // SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, COLUMN_KEY, IS_NULLABLE, EXTRA
                String name = resultSet.getString("COLUMN_NAME");
                String dataType = resultSet.getString("DATA_TYPE");
                String maxLengthString = resultSet.getString("CHARACTER_MAXIMUM_LENGTH");
                String columnKey = resultSet.getString("COLUMN_KEY");
                String isNullableString = resultSet.getString("IS_NULLABLE");
                String extra = resultSet.getString("EXTRA");

                Column column = new Column();
                column.setName(name);

                String propertyName = Utils.toPascalCase(name);
                column.setPropertyName(propertyName);

                String propertyType = Utils.getPropertyType(dataType, maxLengthString, isNullableString);
                column.setPropertyType(propertyType);

                boolean isNullable = Utils.getIsNullable(isNullableString);
                column.setIsNullable(isNullable);

                boolean isVPrimaryKey = Utils.getIsVPrimaryKey(columnKey);
                column.setIsPk(isVPrimaryKey);

                columns.add(column);
            }

        } catch (Exception e){
            queryData.closeResources();
            e.printStackTrace();
        }

        queryData.closeResources();
        return columns;
    }

    /**
     * Gets all module mapping.
     *
     * @param account The credential object.
     * @return Mapping of table names and SugarCRM modules.
     */
    public Map<String, String> getAllModules(Account account) {
        Map<String, String> tableModuleMap = new HashMap<String, String>();
        SqlQueryData queryData =  new SqlQueryData();

        try
        {
            ResultSet resultSet = DataAccess.getRelationshipResultSet(account, queryData);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                // LHS_MODULE, LHS_TABLE, RHS_MODULE, RHS_TABLE
                String lhs_module = resultSet.getString("LHS_MODULE");
                String lhs_table = resultSet.getString("LHS_TABLE");
                String rhs_module = resultSet.getString("RHS_MODULE");
                String rhs_table = resultSet.getString("RHS_TABLE");

                if (!tableModuleMap.containsKey(lhs_table)) {
                    tableModuleMap.put(lhs_table, lhs_module);
                }

                if (!tableModuleMap.containsKey(rhs_table)) {
                    tableModuleMap.put(rhs_table, rhs_module);
                }
            }

        } catch (Exception e){
            queryData.closeResources();
            e.printStackTrace();
        }

        queryData.closeResources();
        return tableModuleMap;
    }
}
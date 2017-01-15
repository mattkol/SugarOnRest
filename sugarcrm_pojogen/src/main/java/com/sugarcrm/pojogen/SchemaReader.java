package com.sugarcrm.pojogen;

import com.mysql.jdbc.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kolao_000 on 2016-12-29.
 */
public class SchemaReader {

    public List<Table> getSchemaTables(Account account)  {
        List<Table> tables = new ArrayList<Table>();

        try
        {
            ResultSet resultSet = DataAccess.getSchemaResultSet(account);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println(columnCount);
            System.out.println("");

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

                System.out.println(schema + ":" + tableName + ":" + className + ":" + isView);
                System.out.println("");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return tables;
    }

    public List<Column> getTableColumns(Account account, String schema, String tablename)  {

        List<Column> columns = new ArrayList<Column>();

        try
        {
            ResultSet resultSet = DataAccess.getTableResultSet(account, schema, tablename);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println(columnCount);
            System.out.println("");

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

                System.out.println(name + ":"  + propertyName + ":" + dataType + ":" + propertyType + " Nullable :" + isNullable + " Pri :" + isVPrimaryKey + " Extra: " + extra);
                System.out.println("");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return columns;
    }

    public Map<String, String> getAllModules(Account account) {
        Map<String, String> tableModuleMap = new HashMap<String, String>();

        try
        {
            ResultSet resultSet = DataAccess.getRelationshipResultSet(account);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println(columnCount);
            System.out.println("");

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
            e.printStackTrace();
        }

        return tableModuleMap;
    }
}
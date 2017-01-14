package com.sugarcrm.pojogen;

import com.mysql.jdbc.StringUtils;

/**
 * Created by kolao_000 on 2016-12-29.
 */
public final class Utils {

    public static String NewLine = System.getProperty("line.separator");

    /**
     * Make get property name prettier.
     *
     *  @param name Name to turn to Pascal casem>
     *
     *  @return Name in Pascal case
     */
    public static String toPascalCase(String name) throws Exception {
        if (StringUtils.isNullOrEmpty(name)) {
            return name;
        }

        String pascalCase = "";
        char newChar;
        boolean toUpper = false;
        char[] charArray = name.toCharArray();
        for (int ctr = 0; ctr <= charArray.length - 1; ctr++)
        {
            if (ctr == 0)
            {
                newChar = Character.toUpperCase(charArray[ctr]);
                pascalCase = Character.toString(newChar);
                continue;
            }

            if (charArray[ctr] == '_')
            {
                toUpper = true;
                continue;
            }

            if (toUpper)
            {
                newChar = Character.toUpperCase(charArray[ctr]);
                pascalCase += Character.toString(newChar);
                toUpper = false;
                continue;
            }

            pascalCase += Character.toString(charArray[ctr]);
        }

        return pascalCase;
    }

    /**
     * Gets the property type from the column.
     *
     *  @return Property type
     */
    public static String getPropertyType(String dataType, String charCountString, String isNullableString)  {
        String propType = "String";
        dataType = getEmptyIfNull(dataType).toLowerCase();
        int charCount = getInteger(charCountString);
        boolean isNullable = getIsNullable(isNullableString);

        if (dataType.equals("char"))
        {
            propType = (charCount == 1) ? "char" : "String";
        }
        else if (dataType.equals("bigint"))
        {
            propType = isNullable  ? "Long" : "long";
        }
        else if (dataType.equals("tinyint") || dataType.equals("smallint") || dataType.equals("int"))
        {
            propType = isNullable  ? "Integer" : "int";;
        }
        else if (dataType.equals("guid"))
        {
            propType = "Guid";
        }
        else if (dataType.equals("time") || dataType.equals("date") || dataType.equals("smalldatetime")  || dataType.equals("datetime") || dataType.equals("timestamp"))
        {
            propType = "Date";
        }
        else if (dataType.equals("float"))
        {
            propType = isNullable  ? "Float" : "float";
        }
        else if (dataType.equals("double"))
        {
            propType = isNullable  ? "Double" :"double";
        }
        else if (dataType.equals("numeric") || dataType.equals("smallmoney") || dataType.equals("decimal") || dataType.equals("money"))
        {
            propType = "BigDecimal";
        }
        else if (dataType.equals("bit") || dataType.equals("bool") || dataType.equals("boolean"))
        {
            propType = isNullable ? "Boolean" : "boolean";
        }
        else if (dataType.equals("image") || dataType.equals("binary") || dataType.equals("blob") || dataType.equals("mediumblob") || dataType.equals("longblob") || dataType.equals("varbinary"))
        {
            propType = "byte[]";
        }

        return propType;
    }

    public static boolean getIsView(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }

        if (value.equalsIgnoreCase("View")) {
            return true;
        }

        return false;
    }

    public static boolean getIsVPrimaryKey(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }

        if (value.equalsIgnoreCase("PRI")) {
            return true;
        }

        return false;
    }

    public static boolean getIsNullable(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }

        if (value.equalsIgnoreCase("YES")) {
            return true;
        }

        return false;
    }

    /**
     * Gets the property type from the column.
     *
     *  @return Property type
     */
    public static String getExtraPackage(String propertyName)  {
        String propType = "String";
        propType = getEmptyIfNull(propertyName);

        if (propType.equals("BigInteger"))
        {
            propType = "java.math.BigInteger";
        }
        else if (propType.equals("Date"))
        {
            propType = "java.util.Date";
        }
        else
        {
            propType = "";
        }

        return propType;
    }

    private static String getEmptyIfNull(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return "";
        }

        return value;
    }

    private static int getInteger(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return 0;
        }

        if (value.equalsIgnoreCase("null")) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        }
        catch (Exception exception) {

        }

        return Integer.MAX_VALUE;
    }
}


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

import com.mysql.jdbc.StringUtils;


public final class Utils {

    public static String NewLine = System.getProperty("line.separator");

    /**
     * Make get property name prettier.
     *
     * @param name Value to convert.
     * @return Name in Pascal case
     * @throws Exception
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
     * Gets the Java pojo column property type from sql type.
     *
     * @param dataType The sql data type.
     * @param charCountString The character length from sql.
     * @param isNullableString Is column nullable.
     * @return Pojo type.
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

    /**
     *  Is table a view?
     *
     * @param value The value from sql.
     * @return True or false.
     */
    public static boolean getIsView(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }

        if (value.equalsIgnoreCase("View")) {
            return true;
        }

        return false;
    }

    /**
     * Is column a primary key?
     *
     * @param value The value from sql query.
     * @return True or false.
     */
    public static boolean getIsVPrimaryKey(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return false;
        }

        if (value.equalsIgnoreCase("PRI")) {
            return true;
        }

        return false;
    }

    /**
     * Is column nullable?
     *
     * @param value The value from sql query.
     * @return True or false.
     */
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
     * Gets java class extra package name.
     *
     * @param propertyName The sql property description.
     * @return The package name.
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

    /**
     * Returns empty string if string value is null.
     *
     * @param value The value to convert.
     * @return The converted string value.
     */
    private static String getEmptyIfNull(String value) {

        if (StringUtils.isNullOrEmpty(value)) {
            return "";
        }

        return value;
    }

    /**
     * Gets integer value from string data.
     *
     * @param value The value to convert.
     * @return The converted string value.
     */
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


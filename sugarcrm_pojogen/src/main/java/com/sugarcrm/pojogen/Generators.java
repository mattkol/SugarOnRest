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

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class Generators {

    public static void generateModules(String destFolder) throws Exception {

        Map<String, String> allModules = readAllModules();
        List<Table> tableList = getTables();
        ClassLoader loader = Generators.class.getClassLoader();
        URL templateUrl = loader.getResource("ModuleTemplate.stg");
        STGroupFile group = getTemplateGroupFile(templateUrl.getPath());

        for (Table table : tableList) {

            String tablename = table.getName();
            String className = Utils.toPascalCase(table.getName());
            String modulename = "";

            if (allModules.containsKey(tablename)) {
                className = allModules.get(tablename);
                modulename = className;
            }

            String joinedPath = new File(destFolder,  className + ".java").toString();

            group.unload();
            String classContent = getClassContent(table, group, className, modulename);

            File file = new File(joinedPath);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(classContent);
            bw.close();
        }
    }

    public static void generateNameOfClass(String destFolder) throws Exception {

        ClassLoader loader = Generators.class.getClassLoader();
        URL templateUrl = loader.getResource("NameOfTemplate.stg");
        StringBuilder stringBuilder = new StringBuilder();

        STGroupFile group = getTemplateGroupFile(templateUrl.getPath());

        // Class comment start
        ST template = group.getInstanceOf("templateComment");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        // Package
        template = group.getInstanceOf("packageLine");
        template.add("packageLine", "package com.sugaronrest;");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);
        stringBuilder.append(Utils.NewLine);

        // Class start
        template = group.getInstanceOf("classStart");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        List<Table> tableList = getTables();
        for (Table table : tableList) {

            // Inner class start
            template = group.getInstanceOf("innerClassStart");
            template.add("name", table.getClassName());
            stringBuilder.append(template.render());
            stringBuilder.append(Utils.NewLine);

            // Set Modulename
            template = group.getInstanceOf("property");
            template.add("name", "SugarModuleName");
            template.add("columnname", table.getName());
            stringBuilder.append(template.render());
            stringBuilder.append(Utils.NewLine);

            // Set properties
            for (Column column : table.getColumns()) {
                template = group.getInstanceOf("property");
                template.add("name", column.getPropertyName());
                template.add("columnname", column.getName());
                stringBuilder.append(template.render());
                stringBuilder.append(Utils.NewLine);
            }

            // Inner class end
            template = group.getInstanceOf("innerClassEnd");
            stringBuilder.append(template.render());
            stringBuilder.append(Utils.NewLine);

            stringBuilder.append(Utils.NewLine);
        }

        // Class end
        template = group.getInstanceOf("classEnd");
        stringBuilder.append(template.render());

        String joinedPath = new File(destFolder,  "NameOf.java").toString();

        File file = new File(joinedPath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringBuilder.toString());
        bw.close();
    }

    public static void generateModuleMapper(String destFolder) throws Exception {

        ClassLoader loader = Generators.class.getClassLoader();
        URL templateUrl = loader.getResource("ModuleMapperTemplate.stg");
        StringBuilder stringBuilder = new StringBuilder();

        STGroupFile group = getTemplateGroupFile(templateUrl.getPath());

        // Class comment start
        ST template = group.getInstanceOf("packageAndImport");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        // Class start
        template = group.getInstanceOf("classStart");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        Map<String, String> allModules = readAllModules();
        List<Table> tableList = getTables();
        for (Table table : tableList) {
            String tablename = table.getName();
            String modulename = Utils.toPascalCase(table.getName());

            if (allModules.containsKey(tablename)) {
                modulename = allModules.get(tablename);

                // Add to map
                template = group.getInstanceOf("addToMap");
                template.add("tablename", tablename);
                template.add("modulename", modulename);
                stringBuilder.append(template.render());

                stringBuilder.append(Utils.NewLine);
            }
        }

        // Class end
        template = group.getInstanceOf("classEnd");
        stringBuilder.append(template.render());

        String joinedPath = new File(destFolder,  "ModuleMapper.java").toString();

        File file = new File(joinedPath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringBuilder.toString());
        bw.close();
    }


    private static List<Table> getTables() {

        Account account = new Account();
        account.setUsername("root");
        account.setPassword("pass4word01");

        SchemaReader schemaReader = new SchemaReader();
        return schemaReader.getSchemaTables(account);
    }

    private static String getClassContent(Table table, STGroupFile group, String className, String modulename) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder();

        // Class comment start
        ST template = group.getInstanceOf("templateComment");
        template.add("tablename", table.getName());
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        // Packages
        template = group.getInstanceOf("packageLine");
        template.add("packageLine", "package com.sugaronrest.modules;");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);
        stringBuilder.append(Utils.NewLine);


        // Add extra packages
        StringBuilder packagesBuilder = new StringBuilder();
        List<String> extraPackages = table.getExtraPackages();
        if ((extraPackages != null) && (extraPackages.size() > 0)) {
            for (String packageName : extraPackages) {
                packagesBuilder.append(String.format("import %s;", packageName));
                packagesBuilder.append(Utils.NewLine);
            }
        }

        template = group.getInstanceOf("importLines");
        template.add("importLines", packagesBuilder.toString());
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        // Class start
        template = group.getInstanceOf("classStart");
        template.add("name", className);
        template.add("modulename", modulename);
        template.add("tablename", table.getName());
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        // Set properties
        for (Column column : table.getColumns()) {
            template = group.getInstanceOf("property");
            template.add("name", column.getPropertyName());
            template.add("columnname", column.getName());
            template.add("backingField", column.getBackingFieldName());
            template.add("type", column.getPropertyType());
            stringBuilder.append(template.render());
        }

        stringBuilder.append(Utils.NewLine);

        // Set backing field properties
        StringBuilder backingFieldsBuilder = new StringBuilder();
        for (Column column : table.getColumns()) {
            String dateField = column.getPropertyType();
            if (dateField.equalsIgnoreCase("Date")) {
                template = group.getInstanceOf("backingDateField");
            }
            else {
                template = group.getInstanceOf("backingField");
            }

            template.add("columnname", column.getName());
            template.add("field", column.getBackingFieldName());
            template.add("type", column.getPropertyType());
            backingFieldsBuilder.append(template.render());
            backingFieldsBuilder.append(Utils.NewLine);
        }

        stringBuilder.append(backingFieldsBuilder.toString());
        backingFieldsBuilder.append(Utils.NewLine);

        // Class end
        template = group.getInstanceOf("classEnd");
        stringBuilder.append(template.render());
        stringBuilder.append(Utils.NewLine);

        return stringBuilder.toString();
    }

    private static Map<String, String> readAllModules()  {
        Account account = new Account();
        account.setUsername("root");
        account.setPassword("pass4word01");

        SchemaReader schemaReader = new SchemaReader();
        Map<String, String> tableModuleMap = schemaReader.getAllModules(account);

        return tableModuleMap;
    }

    private static STGroupFile getTemplateGroupFile(String templatePath) throws MalformedURLException {

        if (templatePath == null) {
             return null;
        }

        if (!templatePath.contains(".jar!")) {
            return new STGroupFile(templatePath);
        }

        String protocol = "jar:";
        if (templatePath.startsWith(protocol)) {
            protocol = "";
        }

        URL url = new URL(protocol + templatePath);
        return  new STGroupFile(url, "US-ASCII", '%', '%');
    }
}

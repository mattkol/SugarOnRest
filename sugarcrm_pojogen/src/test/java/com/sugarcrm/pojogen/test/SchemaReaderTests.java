package com.sugarcrm.pojogen.test;

import com.sugarcrm.pojogen.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by kolao_000 on 2016-12-21.
 */
public class SchemaReaderTests {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void fileReaderTest() throws IOException {

        ClassLoader loader = this.getClass().getClassLoader();
        InputStream in = loader.getResourceAsStream("ModuleTemplate.stg");

        URL main = loader.getResource("ModuleTemplate.stg");
        System.out.println(main.getFile());
        System.out.println(main.getPath());

        Scanner s = new Scanner(in).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        System.out.println(result);
    }

    @Test
    public void generateModuleTest() throws Exception {

        List<Table> tableList = getTables();
        for (Table table : tableList) {
            String folder = "C:\\Logs\\SugarCrm";
            String joinedPath = new File(folder,  table.getClassName() + ".java").toString();

            String classContent = getClassContent(table);

            File file = new File(joinedPath);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(classContent);
            bw.close();
        }
    }

    @Test
    public void generateNameOfClassTest() throws Exception {

        ClassLoader loader = this.getClass().getClassLoader();
        URL templateUrl = loader.getResource("NameOfTemplate.stg");
        StringBuilder stringBuilder = new StringBuilder();

        STGroupFile group = new STGroupFile(templateUrl.getPath());

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

        String folder = "C:\\Logs\\NameOf";
        String joinedPath = new File(folder,  "NameOf.java").toString();

        File file = new File(joinedPath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(stringBuilder.toString());
        bw.close();
    }

    @Test
    public void readSchemaTest()  {

        try
        {
            Account account = new Account();
            account.setUsername("root");
            account.setPassword("pass4word01");

            SchemaReader schemaReader = new SchemaReader();
            List<Table> tableList = schemaReader.getSchemaTables(account);
            System.out.println(tableList.size());
            System.out.println(tableList);

            List<String> uniqueProperties = new ArrayList<String>();
            for (Table table : tableList) {
                for (String item : table.getExtraPackages()) {
                    if (!uniqueProperties.contains(item)) {
                        uniqueProperties.add(item);
                    }
                }
            }

            System.out.println("Unique type");
            for (String item : uniqueProperties) {
                System.out.println(item);
            }

            System.out.println("");

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @After
    public void tearDown() throws Exception {

    }

    private List<Table> getTables() {

        Account account = new Account();
        account.setUsername("root");
        account.setPassword("pass4word01");

        SchemaReader schemaReader = new SchemaReader();
        return schemaReader.getSchemaTables(account);
    }

    private String getClassContent(Table table) throws Exception {

        ClassLoader loader = this.getClass().getClassLoader();
        URL templateUrl = loader.getResource("ModuleTemplate.stg");
        StringBuilder stringBuilder = new StringBuilder();

        STGroupFile group = new STGroupFile(templateUrl.getPath());

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
        template.add("name", table.getClassName());
        template.add("modulename", Utils.toPascalCase(table.getName()));
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

    private String getFormattedBackingFieldProperties(List<Column> columns)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Column column : columns) {
            String property = column.getPropertyName();
            String backingFieldName = Character.toLowerCase(property.charAt(0)) + property.substring(1);
            stringBuilder.append(String.format("private %s %s;", column.getPropertyType(), backingFieldName));
            stringBuilder.append(Utils.NewLine);
        }

        return stringBuilder.toString();
    }

    private String getFormattedProperties(List<Column> columns)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Column column : columns) {
            stringBuilder.append(column.getBackingFieldName());
            stringBuilder.append(Utils.NewLine);
        }

        return stringBuilder.toString();
    }
}
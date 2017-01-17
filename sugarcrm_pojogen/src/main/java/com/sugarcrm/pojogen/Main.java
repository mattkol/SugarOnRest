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

import java.io.File;

public class Main {

    final static String GeneratedFolderName = "Generated";
    final static String ModulesFolder = "Modules";

    public static void main(String[] args) throws Exception {

        String destFolder = ensureBaseFolderExist(args);
        String genFolder = ensureFolderExist(destFolder, GeneratedFolderName);
        String modulesFolder  = ensureFolderExist(destFolder, ModulesFolder);

        // Generate all SugarCRM Java Pojo modules based
        // On ModuleTemplate.stg template
        Generators.generateModules(modulesFolder);

        // Generate SugarCRM module tablename to module name mapping
        // On ModuleMapperTemplate.stg template
        Generators.generateModuleMapper(genFolder);

        // Generate SugarCRM module Java Pojo field name to json name mapping
        // On NameOfTemplate.stg template
        Generators.generateNameOfClass(genFolder);
    }

    private static String ensureBaseFolderExist(String[] args) throws Exception {
        String baseFolder = "";

        if (args.length > 0 ){
            baseFolder = args[0];
        }

        if (StringUtils.isNullOrEmpty(baseFolder)) {
            baseFolder =  System.getProperty("user.dir");
        }

        return ensureFolderExist(baseFolder, "");
    }

    private static String ensureFolderExist(String baseFolder, String folderName) throws Exception {

        boolean boolFolderCheck = true;
        String folder = new File(baseFolder,  folderName).toString();

        File file = new File(folder);
        if (!file.exists()) {
            boolFolderCheck = new File(folder).mkdirs();
        }

        if (!boolFolderCheck){
            throw new Exception("Destination folder:" + folder + " is not valid!");
        }

        return folder;
    }
}

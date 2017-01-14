package com.sugaronrest.utils;

import com.sugaronrest.restapicalls.ModuleInfo;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by kolao_000 on 2016-12-26.
 */
public class Utils {
    public static List<String> getModuleClassNames(String packagePath) {
        try {
            ClassLoader classLoader = ModuleInfo.class.getClassLoader();
            String path =  classLoader.getResource(packagePath).getPath();
            String jarFilePath = getJarFile(path);
            if (jarFilePath == null) {

                String[] classNames = getResourceListing(ModuleInfo.class, packagePath);

                List<String> classNamesList = new ArrayList<String>();
                for (String name : classNames) {
                    String className = name.replace(".class", StringUtils.EMPTY);
                    classNamesList.add(className);
                }

                return classNamesList;
            }  else {
                return getNamesFromJar(jarFilePath, packagePath);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<String>();
    }

    private static List<String> getNamesFromJar(String jarFilePath, String packagePath)
    {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(URLDecoder.decode(jarFilePath, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration enumeration = jarFile.entries();
        List<String> fileNames = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            process(enumeration.nextElement(), packagePath, fileNames);
        }

        return fileNames;
    }

    private static void process(Object obj, String packagePath, List<String> fileNames)
    {
        JarEntry entry = (JarEntry)obj;
        if (!entry.isDirectory()) {
            String name = entry.getName();
            if (name.contains(packagePath)) {
                File file = new File(name);
                String className = file.getName().replace(".class", StringUtils.EMPTY);
                fileNames.add(className);
            }
        }
    }

    private static String getJarFile(String path) {
        if (path == null) {
            return null;
        }

        if (!path.contains(".jar!")) {
            return null;
        }

        int startIndex = 0;
        if (path.contains("file:/")) {
            startIndex = "file:/".length();
        }

        int firstIndexOf = path.lastIndexOf("file:/");
        int lastIndexOf = path.lastIndexOf('!');
        String jarFilePath = path.substring(startIndex, lastIndexOf);
        return jarFilePath;
    }

    /**
     * http://www.uofr.net/~greg/java/get-resource-listing.html
     *
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     *
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    private static String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
        /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
        /*
         * In case of a jar file, we can't actually find a directory.
         * Have to assume the same jar as clazz.
         */
            String me = clazz.getName().replace(".", "/")+".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
        /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
            while(entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
    }
}

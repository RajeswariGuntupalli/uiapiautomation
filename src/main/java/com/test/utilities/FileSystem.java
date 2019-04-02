package com.test.utilities;

import com.test.hooks.Hooks;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class FileSystem {
    private static String sourceFolder;
    private List<String> fileList;

    public FileSystem() {

    }

    public FileSystem(String sourceFolder) {
        fileList = new ArrayList<String>();
        FileSystem.sourceFolder = sourceFolder;
    }

    public static boolean isFileExist(String path) {
        return new File(path).exists();
    }

    public static String getResourcePath(String resourcePath) throws URISyntaxException {
        URL resource = FileSystem.class.getClassLoader().getResource(resourcePath);
        return resource.toURI().getPath();
    }

    public static String getFeatureDirectory() {
        return "/features/" + Hooks.scenario.getId().split(";")[0].replace("-", "_");
    }

    public static String getFilePrefix() {
        String filePath = StringUtils.substringBefore(Hooks.scenario.getId().replace(";", "/").replace("-", "_"),"//");
        filePath = filePath + "/" + StringUtils.substringAfterLast(filePath, "/");
        return "/features/" + StringUtils.replace(filePath,"___<description>", "");
    }

}

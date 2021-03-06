package com.niopullus.NioLib;

import java.io.*;
import java.util.List;

/**Relay class for an instance of FileManager for transferring files
 * Created by Owen on 6/10/2016.
 */
public class Data {

    private static FileManager fileManager;

    public static void init(final FileManager fm) {
        fileManager = fm;
    }

    public static File getFile(final String dir) {
        return fileManager.getFile(dir);
    }

    public static String getTextFromFile(final String fileDir) {
        return fileManager.getTextFromFile(fileDir);
    }

    public static List<String> getTextByLineFromFile(final String fileDir) {
        return fileManager.getTextByLineFromFile(fileDir);
    }

    public static String getTextFromJar(final String jarDir) {
        return fileManager.getTextFromJar(jarDir);
    }

    public static List<String> getTextByLineFromJar(final String jarDir) {
        return fileManager.getTextByLineFromFile(jarDir);
    }

    public static List<String> getFileNamesFromFile(final String fileDir) {
        return fileManager.getFileNamesFromFile(fileDir);
    }

    public static void writeToFileFromFile(final String fileDir, final List<String> data, final boolean overwrite) {
        fileManager.writeToFileFromFile(fileDir, data, overwrite);
    }

    public static void writeToFileFromFile(final String fileDir, final String data, final boolean overwrite) {
        fileManager.writeToFileFromFile(fileDir, data, overwrite);
    }

    public static void createFileFromFile(final String fileDir, final String fileName) {
        fileManager.createFileFromFile(fileDir, fileName);
    }

    public static void createFolderFromFile(final String fileDir, final String folderName) {
        fileManager.createFolderFromFile(fileDir, folderName);
    }

    public static String getJarDir() {
        return FileManager.getJarDir();
    }

    public static String getJarFolder() {
        return FileManager.getJarFolder();
    }

    public static void deleteFileFromFile(final String fileDir) {
        fileManager.deleteFileFromFile(fileDir);
    }

    public static boolean fileExists(final String fileDir) {
        return fileManager.fileExists(fileDir);
    }

}

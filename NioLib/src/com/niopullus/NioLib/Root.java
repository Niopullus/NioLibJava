package com.niopullus.NioLib;

import java.io.File;
import java.util.List;

/**Relay class for an instance of FileManager for transferring files
 * Created by Owen on 6/23/2016.
 */
public class Root {

    private static FileManager fileManager;
    private static String root;

    public static void init(final FileManager fm, final String rootDir) {
        fileManager = fm;
        root = rootDir;
    }

    public static File getFile(final String dir) {
        return fileManager.getFile(root + dir);
    }

    public static String getTextFromFile(final String fileDir) {
        return fileManager.getTextFromFile(root + fileDir);
    }

    public static List<String> getTextByLineFromFile(final String fileDir) {
        return fileManager.getTextByLineFromFile(root + fileDir);
    }

    public static String getTextFromJar(final String jarDir) {
        return fileManager.getTextFromJar(jarDir);
    }

    public static List<String> getTextByLineFromJar(final String jarDir) {
        return fileManager.getTextByLineFromFile(jarDir);
    }

    public static List<String> getFileNamesFromFile(final String fileDir) {
        return fileManager.getFileNamesFromFile(root + fileDir);
    }

    public static void writeToFileFromFile(final String fileDir, final List<String> data, final boolean overwrite) {
        fileManager.writeToFileFromFile(root + fileDir, data, overwrite);
    }

    public static void writeToFileFromFile(final String fileDir, final String data, final boolean overwrite) {
        fileManager.writeToFileFromFile(root + fileDir, data, overwrite);
    }

    public static void createFileFromFile(final String fileDir, final String fileName) {
        fileManager.createFileFromFile(root + fileDir, fileName);
    }

    public static void createFolderFromFile(final String fileDir, final String folderName) {
        fileManager.createFolderFromFile(root + fileDir, folderName);
    }

    public static String getJarDir() {
        return FileManager.getJarDir();
    }

    public static String getJarFolder() {
        return FileManager.getJarFolder();
    }

    public static void deleteFileFromFile(final String fileDir) {
        fileManager.deleteFileFromFile(root + fileDir);
    }

    public static boolean fileExists(final String fileDir) {
        return fileManager.fileExists(root + fileDir);
    }

}

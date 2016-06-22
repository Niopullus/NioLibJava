package com.niopullus.NioLib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**Relay class for an instance of FileManager
 * Created by Owen on 6/10/2016.
 */
public class Data {

    private static FileManager fileManager = new FileManager();

    public static File getRootFile() {
        return fileManager.getRootFile();
    }

    public static File getFile(final String dir) {
        return fileManager.getFile(dir);
    }

    public static String getTextFromFile(final String fileDir) {
        return fileManager.getTextFromFile(fileDir);
    }

    public static String getTextFromJar(final String jarDir) {
        return fileManager.getTextFromJar(jarDir);
    }

    public static List<String> getFileNamesFromFile(final String fileDir) {
        return fileManager.getFileNamesFromFile(fileDir);
    }

    public static List<String> getFileNamesFromJar(final String jarDir) {
        return fileManager.getFileNamesFromFile(jarDir);
    }

    public static List<String> getFileNames(final String fileDir, final String jarDir) {
        return fileManager.getFileNames(fileDir, jarDir);
    }

    public static List<String> getFileNames(final String dir) {
        return fileManager.getFileNames(dir);
    }

    public static void setRootDir(final String dir) {
        fileManager.setRootDir(dir);
    }

    public static void writeToFileFromFile(final String fileDir, final String data) {
        fileManager.writeToFileFromFile(fileDir, data);
    }

    public static void writeToFileFromJar(final String jarDir, final String data) {
        fileManager.writeToFileFromJar(jarDir, data);
    }

    public static void createFileFromFile(final String fileDir) {
        fileManager.createFileFromJar(fileDir);
    }

    public static void createFileFromJar(final String jarDir) {
        fileManager.createFileFromJar(jarDir);
    }

    public static void createFolderFromFile(final String fileDir) {
        fileManager.createFolderFromFile(fileDir);
    }

    public static void createFolderFromJar(final String jarDir) {
        fileManager.createFolderFromJar(jarDir);
    }

    public static String getJarDir() {
        return FileManager.getJarDir();
    }

    public enum DataRoot {

        FILE,
        JAR

    }

}

package com.niopullus.NioLib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**Manages the various files involved with the program
 * Makes interactions with the files easier
 * Created by Owen on 6/6/2016.
 */
public class FileManager {

    private File rootFile;
    private String rootDir;

    public File getRootFile() {
        return rootFile;
    }

    public File getFile(final String dir) {
        return new File(rootDir + "/" + dir);
    }

    public String getTextFromFile(final String fileDir) {
        final File file = getFile(fileDir);

    }

    public String getTextFromJar(final String jarDir) {

    }

    public List<String> getFileNamesFromFile(final String fileDir) {

    }

    public List<String> getFileNamesFromJar(final String jarDile) {

    }

    public List<String> getFileNames(final String fileDir, final String jarDir) {
        final List<String> fileNamesFromFile = getFileNamesFromFile(fileDir);
        final List<String> fileNamesFromJar = getFileNamesFromJar(jarDir);
        final List all = new ArrayList();
        all.addAll(fileNamesFromFile);
        all.addAll(fileNamesFromJar);
        return all;
    }

    public void setRootDir(final String dir) {
        rootDir = dir;
        rootFile = new File(dir);
    }

    public void writeToFileFromFile(final String fileDir, final String data) {

    }

    public void writeToFileFromJar(final String jarDir, final String data) {

    }

    public void createFileFromFile(final String fileDir) {

    }

    public void createFileFromJar(final String jarDir) {

    }

    public void createFolderFromFile(final String fileDir) {

    }

    public void createFolderFromJar(final String jarDir) {

    }

    public static String getJarDir() {

    }

}

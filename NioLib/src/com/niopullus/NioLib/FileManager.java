package com.niopullus.NioLib;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**Manages the various files involved with the program
 * Makes interactions with the files easier
 * Created by Owen on 6/6/2016.
 */
public class FileManager {

    public File getFile(final String dir) {
        return new File(dir);
    }

    public String getTextFromFile(final String fileDir) {
        final File file = getFile(fileDir);
        String result = "";
        try {
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getTextByLineFromFile(final String fileDir) {
        final File file = getFile(fileDir);
        final List<String> result = new ArrayList<>();
        try {
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            bufferedReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getTextFromJar(final String jarDir) {
        String result = "";
        try {
            final InputStream stream = Main.class.getResourceAsStream(jarDir);
            final InputStreamReader inputStreamReader = new InputStreamReader(stream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getTextByLineFromJar(final String jarDir) {
        final List<String> result = new ArrayList<>();
        try {
            final InputStream stream = Main.class.getResourceAsStream(jarDir);
            final InputStreamReader inputStreamReader = new InputStreamReader(stream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
            bufferedReader.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getFileNamesFromFile(final String fileDir) {
        final File file = getFile(fileDir);
        final File[] subFiles = file.listFiles();
        final List<String> fileNames = new ArrayList<>();
        for (File subFile : subFiles) {
            fileNames.add(subFile.getName());
        }
        return fileNames;
    }

    public void writeToFileFromFile(final String fileDir, final List<String> data, final boolean overwrite) {
        final File file = getFile(fileDir);
        try {
            final FileWriter fileWriter = new FileWriter(file, !overwrite);
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line : data) {
                bufferedWriter.write(line + "\n");
            }
            bufferedWriter.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFileFromFile(final String fileDir, final String data, final boolean overwrite) {
        final File file = getFile(fileDir);
        try {
            final FileWriter fileWriter = new FileWriter(file, !overwrite);
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void createFileFromFile(final String fileDir, final String fileName) {
        System.out.println(fileDir + "/" + fileName);
        try {
            final File file = new File(fileDir + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void createFolderFromFile(final String fileDir, final String folderName) {
        System.out.println(fileDir + "/" + folderName);
        final File file = new File(fileDir + "/" + folderName);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static String getJarDir() {
        try {
            return Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replace("\\", "/");
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJarFolder() {
        try {
            final String path = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            final String jar = path.substring(1, path.length() - 1).replace("\\", "/");
            final String jarPath;
            int index = -1;
            for (int i = 0; i < jar.length(); i++) {
                if ("/".equals(jar.substring(i, i + 1))) {
                    index = i;
                }
            }
            return jar.substring(0, index);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFileFromFile(final String fileDir) {
        final File file = getFile(fileDir);
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean fileExists(final String fileDir) {
        final File file = getFile(fileDir);
        return file.exists();
    }

}

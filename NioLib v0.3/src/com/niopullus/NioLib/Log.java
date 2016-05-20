package com.niopullus.NioLib;

/**Relay class designed to statically manage a LogManager object
 * Created by Owen on 5/16/2016.
 */
public class Log {

    private static LogManager logManager;

    public static void setLogManager(final LogManager logManager) {
        Log.logManager = logManager;
    }

    public static void doc(final String message, final String source, final LogManager.LogType type) {
        logManager.doc(message, source, type);
    }

    public static String read() {
        return logManager.read();
    }

}

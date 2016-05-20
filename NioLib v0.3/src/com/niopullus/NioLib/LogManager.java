package com.niopullus.NioLib;

import java.util.ArrayList;

/**Keeps track of log items, retrievable via read()
 * Created by Owen on 5/16/2016.
 */
public class LogManager {

    private ArrayList<LogItem> items;
    private int readPos;

    public LogManager() {
        items = new ArrayList<>();
        readPos = 0;
    }

    public void doc(final String message, final String source, final LogType type) {
        final LogItem item = new LogItem(message, source, type);
        items.add(item);
    }

    public String read() {
        final LogItem item = items.get(readPos);
        readPos++;
        return item.toString();
    }

    private class LogItem {

        private String message;
        private String source;
        private LogType type;

        public LogItem(final String message, final String source, final LogType type) {
            this.message = message;
            this.source = source;
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public String getSource() {
            return source;
        }

        public LogType getType() {
            return type;
        }

        public String toString() {
            String result = "";
            result += "[" + type + "] [" + source + "] :" + message;
            return result;
        }

    }

    public enum LogType {

        INFO("INFO"),
        WARNING("WARNING"),
        ERROR("ERROR"),
        DEBUG("DEBUG");

        private String name;

        LogType(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

    }

}
